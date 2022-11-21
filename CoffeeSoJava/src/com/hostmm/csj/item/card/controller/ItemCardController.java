package com.hostmm.csj.item.card.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hostmm.csj.cashier.controller.CashierMainController;
import com.hostmm.csj.item.controller.OrderedItemController;
import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.OrderedItem;
import com.jfoenix.controls.JFXRadioButton;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class ItemCardController implements Initializable {

	@FXML
	private ImageView imgviewCoffee;

	@FXML
	private Label lblCoffeeName;

	@FXML
	private Label lblDescription;

	@FXML
	private ToggleGroup mood;

	@FXML
	private JFXRadioButton rbCold;

	@FXML
	private JFXRadioButton rbHot;

	@FXML
	private Label lblPrice;

	@FXML
	private TextField tfQuantity;

	private Item item = Item.getItemInstance();
	private OrderedItem orderedItem = OrderedItem.getInstance();
	private int quantity;
	String name;
	double totalPrice;
	int index;
	static StringProperty subTotal = new SimpleStringProperty("$ 0");
	private double subT;

	@FXML
	void processDecrease(ActionEvent event) {
		if (quantity > 0) {
			quantity -= 1;
			tfQuantity.setText(String.valueOf(quantity));
		}
	}

	@FXML
	void processIncrease(ActionEvent event) {
		quantity += 1;
		tfQuantity.setText(String.valueOf(quantity));
	}

	@FXML
	void processAddtoBill(ActionEvent event) {
		subT = 0;

		if (mood.getSelectedToggle() != null && Double.parseDouble(tfQuantity.getText()) > 0) {
			CashierMainController.getBillFlowPane().getChildren().clear();
			name = lblCoffeeName.getText() + "(" + mood.getSelectedToggle().getUserData().toString() + ")";
			totalPrice = Integer.parseInt(tfQuantity.getText()) * Double.parseDouble(lblPrice.getText());
			boolean addNewOrder = false;
			for (OrderedItem oi : OrderedItem.getListInstance()) {
				if (!oi.getName().equalsIgnoreCase(name)) {
					addNewOrder = true;
				}
				if (oi.getName().equalsIgnoreCase(name)) {
					addNewOrder = false;
					index = OrderedItem.getListInstance().indexOf(oi);
					break;
				}
			}

			if (OrderedItem.getListInstance().size() == 0)
				addNewOrder = true;

			if (addNewOrder) {
				OrderedItem.getListInstance().add(new OrderedItem(name, quantity, totalPrice));
			} else {
				OrderedItem.getListInstance().get(index).setName(name);
				OrderedItem.getListInstance().get(index).setQuantity(quantity);
				OrderedItem.getListInstance().get(index).setTotalPrice(totalPrice);
			}

			for (OrderedItem oi : OrderedItem.getListInstance()) {
				subT += oi.getTotalPrice();
				setSubTotal(subT);
				this.orderedItem.setName(oi.getName());
				this.orderedItem.setQuantity(oi.getQuantity());
				this.orderedItem.setTotalPrice(oi.getTotalPrice());
				try {
					AnchorPane root = FXMLLoader
							.load(getClass().getResource("/com/hostmm/csj/item/view/OrderedItemView.fxml"));
					CashierMainController.getBillFlowPane().getChildren().add(root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning Message");
			alert.setContentText("Make sure to choose your mood and quantity");
			alert.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quantity = Integer.parseInt(tfQuantity.getText());
		rbCold.setUserData("cold");
		rbHot.setUserData("hot");

		lblCoffeeName.setText(this.item.getName().toUpperCase());
		lblDescription.setText(this.item.getDescription());
		lblPrice.setText(String.valueOf(this.item.getPrice()));
		if (this.item.getMood().equalsIgnoreCase("both")) {
			rbHot.setVisible(true);
			rbCold.setVisible(true);
		} else if (this.item.getMood().equalsIgnoreCase("hot")) {
			rbHot.setVisible(true);
			rbCold.setVisible(false);
		} else {
			rbHot.setVisible(false);
			rbCold.setVisible(true);
		}
		File imageFile = new File("src/com/hostmm/csj/item/image/" + this.item.getImageName());
		if (imageFile != null) {
			Image imageProfile = new Image(imageFile.getAbsolutePath());
			imgviewCoffee.setImage(imageProfile);
		}
		
//		subTotal.bind(OrderedItemController.getSubTotal());
	}

	public static StringProperty getSubTotal() {
		return subTotal;
	}

	public static void setSubTotal(double subTotal) {
		ItemCardController.subTotal.set("$ "+String.valueOf(subTotal));
	}

}
