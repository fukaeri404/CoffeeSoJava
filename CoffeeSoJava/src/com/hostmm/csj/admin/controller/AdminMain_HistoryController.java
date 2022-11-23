package com.hostmm.csj.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

import com.hostmm.csj.bill.model.Bill;
import com.hostmm.csj.bill.model.BillDAO;
import com.hostmm.csj.item.model.OrderedItem;
import com.hostmm.csj.staff.model.Staff;
import com.hostmm.csj.utility.notification.MyNotification;
import com.hostmm.csj.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminMain_HistoryController implements Initializable {

	@FXML
	private TableColumn<Bill, String> cashier;

	@FXML
	private JFXComboBox<String> cobDate;

	@FXML
	private JFXComboBox<String> cobMonth;

	@FXML
	private JFXComboBox<String> cobYear;

	@FXML
	private TableColumn<Bill, String> name;

	@FXML
	private TableColumn<Bill, Integer> quantity;

	@FXML
	private TableColumn<Bill, String> saleDate;

	@FXML
	private TableColumn<Bill, String> saleMonth;

	@FXML
	private TableColumn<Bill, Time> saleTime;

	@FXML
	private TableColumn<Bill, String> saleYear;

	@FXML
	private TableColumn<Bill, String> totalPrice;

	@FXML
	private TableView<Bill> tvHistory;

	private BillDAO billDAO = new BillDAO();
	private Bill bill = Bill.getBillinstance();
	private MyNotification myNoti = new MyNotification();

	@FXML
	void processAdd(ActionEvent event) {
		try {
			AnchorPane ap = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/bill/view/BillRegisterView.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(ap);
			stage.setScene(scene);
			stage.show();
			stage.showingProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (!newValue) {
						refresh();
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void processDelete(ActionEvent event) {

		Bill selectedBill = tvHistory.getSelectionModel().getSelectedItem();
		if (selectedBill != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure to delete?");
			Optional<ButtonType> action = alert.showAndWait();
			if (action.get() == ButtonType.OK) {
				int rowEffected = this.billDAO.deleteItemById(selectedBill);
				if (rowEffected > 0) {
					String messageTitle = "Deleted";
					String message = "successfully deleted Bill";
					MyNotificationType notitype = MyNotificationType.SUCCESS;
					Duration dismissTime = Duration.seconds(3);
					myNoti.getNotification(messageTitle, message, notitype, dismissTime);
					refresh();
				}
			}

		}

	}

	@FXML
	void processEdit(ActionEvent event) {
		Bill selectedBill = tvHistory.getSelectionModel().getSelectedItem();
		if (selectedBill != null) {
			this.bill.setId(selectedBill.getId());
			this.bill.setName(selectedBill.getName());
			this.bill.setQuantity(selectedBill.getQuantity());
			this.bill.setTotalPrice(selectedBill.getTotalPrice());
			this.bill.setSaleMonth(selectedBill.getSaleMonth());
			this.bill.setSaleDate(selectedBill.getSaleDate());
			this.bill.setSaleYear(selectedBill.getSaleYear());
			this.bill.setCashier(selectedBill.getCashier());
			this.bill.setSaleTime(selectedBill.getSaleTime());
			this.bill.setStatus(selectedBill.getStatus());
			try {
				AnchorPane register = FXMLLoader
						.load(getClass().getResource("/com/hostmm/csj/bill/view/BillRegisterView.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(register);
				stage.setScene(scene);
				stage.show();
				stage.showingProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (!newValue) {
							refresh();
							bill.setStatus("");
						}
					}

				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void processRefresh(ActionEvent event) {
		refresh();
	}

	private void refresh() {
		showTable("select * from history");
	}

	private void showTable(String sql) {
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		saleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
		saleMonth.setCellValueFactory(new PropertyValueFactory<>("saleMonth"));
		saleYear.setCellValueFactory(new PropertyValueFactory<>("saleYear"));
		saleTime.setCellValueFactory(new PropertyValueFactory<>("saleTime"));
		cashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
		ObservableList<Bill> staffList = this.billDAO.getBillList(sql);
		tvHistory.setItems(staffList);
//		tfSearch.textProperty().addListener(
//				(observable, oldValue, newValue) -> tvUser.setItems(filterList(userList, newValue.toLowerCase())));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable("select * from history");
	}

}
