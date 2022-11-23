package com.hostmm.csj.cashier.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.hostmm.csj.bill.model.Bill;
import com.hostmm.csj.bill.model.BillDAO;
import com.hostmm.csj.item.card.controller.ItemCardController;
import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.ItemDAO;
import com.hostmm.csj.item.model.OrderedItem;
import com.hostmm.csj.login.model.LoginDAO;
import com.hostmm.csj.utility.notification.MyNotification;
import com.hostmm.csj.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.animation.RotateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CashierMainController implements Initializable {

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private Pane paneCoffee;

	@FXML
	private FlowPane flowPane;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Label lblResult;

	@FXML
	private Label lblSubTotal;

	@FXML
	private Label lblTax;

	@FXML
	private Label lblTotal;

	@FXML
	private ImageView imgviewCashier;

	@FXML
	private TextField tfSearch;

	@FXML
	private Label lblUsername;

	@FXML
	private Rectangle rectangle;

	private ItemDAO itemDAO = new ItemDAO();
	private BillDAO billDAO = new BillDAO();
	private Item item = Item.getItemInstance();
	private static FlowPane billFlowPane = new FlowPane();
	private MyNotification myNoti = new MyNotification();
	RotateTransition rotate = new RotateTransition();

	@FXML
	void processPrint(ActionEvent event) {
		int rowEffected = 0;
		for (OrderedItem oi : OrderedItem.getListInstance()) {
			Bill bill = new Bill(oi.getName(), oi.getQuantity(), oi.getTotalPrice(),
					String.valueOf(LocalDate.now().getMonthValue()), String.valueOf(LocalDate.now().getDayOfMonth()),
					String.valueOf(LocalDate.now().getYear()), LocalTime.now(), LoginDAO.getLoggedUsername(), "created");
			rowEffected = billDAO.createBill(bill);
		}
		if (rowEffected > 0) {
			billFlowPane.getChildren().clear();
			OrderedItem.getListInstance().clear();

			addItemCard("select * from coffee where available = 'available';");

			String message = "successfully printed bill";
			MyNotificationType notitype = MyNotificationType.SUCCESS;
			Duration dismissTime = Duration.seconds(3);
			myNoti.getNotification("Printed", message, notitype, dismissTime);

			ItemCardController.setSubTotal(0);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblUsername.setText(LoginDAO.getLoggedUsername());
		File imageFile = new File("src/com/hostmm/csj/staff/profile/" + LoginDAO.getLoggedUserImage());
		if (imageFile != null) {
			Image imageProfile = new Image(imageFile.getAbsolutePath());
			imgviewCashier.setImage(imageProfile);
		}
		billFlowPane.setPrefWidth(270);
		billFlowPane.setStyle("-fx-background-color : transparent");
		scrollPane.setContent(billFlowPane);
		drawer.open();
		setSidePane();
		addItemCard("select * from coffee where available = 'available';");
		paneCoffee.setOnMouseClicked((e) -> {

		});
		tfSearch.setOnKeyReleased((e) -> {
			if (tfSearch.getText().isBlank())
				addItemCard("select * from coffee where available = 'available';");
			else
				addItemCard(
						"select * from coffee where name = '" + tfSearch.getText() + "' && available = 'available';");
		});

		lblSubTotal.textProperty().bind(ItemCardController.getSubTotal());

		lblSubTotal.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String[] split = lblSubTotal.getText().split(" ");
				double subTotal = Double.parseDouble(split[1]);
				double tax = subTotal / 10;
				lblTax.setText("$ " + String.valueOf(tax));
				lblTotal.setText("$ " + String.valueOf(subTotal + tax));
			}
		});

	}

	private void addItemCard(String sql) {
		int result = 0;
		flowPane.getChildren().clear();
		ObservableList<Item> itemList = itemDAO.getItemList(sql);
		for (Item item : itemList) {
			result += 1;
			this.item.setName(item.getName());
			this.item.setMood(item.getMood());
			this.item.setPrice(item.getPrice());
			this.item.setDescription(item.getDescription());
			this.item.setImageName(item.getImageName());
			try {
				AnchorPane card = FXMLLoader
						.load(getClass().getResource("/com/hostmm/csj/item/card/view/ItemCardView.fxml"));
				flowPane.getChildren().add(card);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lblResult.setText(result + " Coffees Result");
	}

	private void setSidePane() {

		try {
			VBox sidepane = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/cashier/view/CashierSideBar.fxml"));
			drawer.setSidePane(sidepane);
			HamburgerBackArrowBasicTransition task = new HamburgerBackArrowBasicTransition(hamburger);
			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				task.setRate(task.getRate() * -1);
				task.play();
				rotate.setAxis(Rotate.Z_AXIS);
				rotate.setDuration(Duration.millis(1000));
				rotate.setNode(rectangle);
				rotate.setAutoReverse(true);
				if (drawer.isShown()) {
					drawer.close();
					rotate.setByAngle(-180);
					rotate.play();
				} else {
					rotate.setByAngle(180);
					rotate.play();
					drawer.open();
				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static FlowPane getBillFlowPane() {
		return billFlowPane;
	}

}
