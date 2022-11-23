package com.hostmm.csj.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

import com.hostmm.csj.bill.model.Bill;
import com.hostmm.csj.bill.model.BillDAO;
import com.hostmm.csj.item.model.OrderedItem;
import com.hostmm.csj.staff.model.Staff;
import com.hostmm.csj.utility.notification.MyNotification;
import com.hostmm.csj.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
	private JFXTextField tfSearch;

	@FXML
	private Label lblTotalPrice;

	@FXML
	private TableView<Bill> tvHistory;

	private BillDAO billDAO = new BillDAO();
	private Bill bill = Bill.getBillinstance();
	private MyNotification myNoti = new MyNotification();
	private double totalP;

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
		cobMonth.setValue("");
		cobDate.setValue("");
		cobYear.setValue("");
		cobMonth.setPromptText("Month");
		cobDate.setPromptText("Date");
		cobYear.setPromptText("Year");
		tfSearch.setText("");
		tfSearch.setPromptText("enter cashier name");
	}

	private void showTable(String sql) {
		totalP = 0;
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		saleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
		saleMonth.setCellValueFactory(new PropertyValueFactory<>("saleMonth"));
		saleYear.setCellValueFactory(new PropertyValueFactory<>("saleYear"));
		saleTime.setCellValueFactory(new PropertyValueFactory<>("saleTime"));
		cashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
		ObservableList<Bill> staffList = this.billDAO.getBillList(sql);

		for (Bill bill : staffList) {
			totalP += bill.getTotalPrice();
		}

		lblTotalPrice.setText("$ " + totalP);
		tvHistory.setItems(staffList);
//		tfSearch.textProperty().addListener(
//				(observable, oldValue, newValue) -> tvUser.setItems(filterList(userList, newValue.toLowerCase())));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable("select * from history");
		ObservableList<String> monthList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12");
		cobMonth.setItems(monthList);
		ObservableList<String> dayList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
				"25", "26", "27", "28", "29", "30", "31");
		cobDate.setItems(dayList);
		ObservableList<String> yearList = FXCollections.observableArrayList("2022", "2023", "2024", "2025");
		cobYear.setItems(yearList);

		cobMonth.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!cobMonth.getValue().isBlank()) {
					//
					if ((cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						System.out.println("1");
						showTable("select * from history where saleMonth = '" + cobMonth.getValue() + "';");
					}
//Only other one not null
					if ((cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleMonth = '" + cobMonth.getValue()
								+ "' && saleDate = '" + cobDate.getValue() + "';");
					}

					if ((cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleMonth = '" + cobMonth.getValue()
								+ "' && saleYear = '" + cobYear.getValue() + "';");
					}

					if ((cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleMonth = '" + cobMonth.getValue() + "' && cashier = '"
								+ tfSearch.getText() + "';");
					}

//Only Two Not null
					if ((cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable(
								"select * from history where saleMonth = '" + cobMonth.getValue() + "' && saleDate = '"
										+ cobDate.getValue() + "' && saleYear = '" + cobYear.getValue() + "';");
					}

					if ((cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable(
								"select * from history where saleMonth = '" + cobMonth.getValue() + "' && saleDate = '"
										+ cobDate.getValue() + "' && cashier = '" + cashier.getText() + "';");
					}

					if ((cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable(
								"select * from history where saleMonth = '" + cobMonth.getValue() + "' && saleYear = '"
										+ cobYear.getValue() + "' && cashier = '" + cashier.getText() + "';");
					}

//All Not Null
					if ((cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleMonth = '" + cobMonth.getValue()
								+ "' && saleDate = '" + cobDate.getValue() + "' && saleYear = '" + cobYear.getValue()
								+ "' && cashier ='" + tfSearch.getText() + "';");
					}

				}
			}
		});

		cobDate.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!cobDate.getValue().isBlank()) {
					//
					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						System.out.println("1");
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "';");
					}
//Only other one not null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && saleYear = '"
								+ cobYear.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && cashier = '"
								+ tfSearch.getText() + "';");
					}

//Only Two Not null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && saleYear = '" + cobYear.getValue() + "';");
					}

					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && cashier = '" + cashier.getText() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && saleYear = '"
								+ cobYear.getValue() + "' && cashier = '" + cashier.getText() + "';");
					}

//All Not Null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && saleYear = '" + cobYear.getValue() + "' && cashier ='"
								+ tfSearch.getText() + "';");
					}

				}

			}
		});

		cobYear.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!cobYear.getValue().isBlank()) {
					//
					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						System.out.println("1");
						showTable("select * from history where saleYear = '" + cobYear.getValue() + "';");
					}
//Only other one not null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleYear = '" + cobYear.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleYear = '" + cobYear.getValue() + "' && saleDate = '"
								+ cobDate.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleYear = '" + cobYear.getValue() + "' && cashier = '"
								+ tfSearch.getText() + "';");
					}

//Only Two Not null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (tfSearch.getText() == null || tfSearch.getText().isBlank())) {
						showTable("select * from history where saleYear = '" + cobYear.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && saleDate = '" + cobDate.getValue() + "';");
					}

					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleYear = '" + cobYear.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && cashier = '" + cashier.getText() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleYear = '" + cobYear.getValue() + "' && saleDate = '"
								+ cobDate.getValue() + "' && cashier = '" + cashier.getText() + "';");
					}

//All Not Null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (tfSearch.getText() != null && !tfSearch.getText().isBlank())) {
						showTable("select * from history where saleYear = '" + cobDate.getValue() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && saleDate = '" + cobDate.getValue() + "' && cashier ='"
								+ tfSearch.getText() + "';");
					}

				}

			}
		});

		tfSearch.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!tfSearch.getText().isBlank()) {
					//
					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "';");
					}
//Only other one not null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "' && saleMonth = '"
								+ cobMonth.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "' && saleDate = '"
								+ cobDate.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "' && saleYear = '"
								+ cobYear.getValue() + "';");
					}

//Only Two Not null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && saleDate = '" + cobDate.getValue() + "';");
					}

					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && saleYear = '" + cobYear.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "' && saleDate = '"
								+ cobDate.getValue() + "' && saleYear = '" + cobYear.getValue() + "';");
					}

//All Not Null
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable("select * from history where cashier = '" + tfSearch.getText() + "' && saleMonth = '"
								+ cobMonth.getValue() + "' && saleDate = '" + cobDate.getValue() + "' && saleYear ='"
								+ cobYear.getValue() + "';");
					}

				} else {
					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history");
					}

					// one
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history where saleMonth ='" + cobMonth.getValue() + "'");
					}
					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history where saleDate ='" + cobDate.getValue() + "'");
					}
					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable("select * from history where saleYear ='" + cobYear.getValue() + "'");
					}
					// two
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() == null || cobYear.getValue().isBlank())) {
						showTable("select * from history where saleMonth = '" + cobMonth.getValue()
								+ "' && saleDate = '" + cobDate.getValue() + "';");
					}

					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() == null || cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable("select * from history where saleMonth = '" + cobMonth.getValue()
								+ "' && saleYear = '" + cobYear.getValue() + "';");
					}

					if ((cobMonth.getValue() == null || cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable("select * from history where saleDate = '" + cobDate.getValue() + "' && saleYear = '"
								+ cobYear.getValue() + "';");
					}

					// three
					if ((cobMonth.getValue() != null && !cobMonth.getValue().isBlank())
							&& (cobDate.getValue() != null && !cobDate.getValue().isBlank())
							&& (cobYear.getValue() != null && !cobYear.getValue().isBlank())) {
						showTable(
								"select * from history where saleMonth = '" + cobMonth.getValue() + "' && saleDate = '"
										+ cobDate.getValue() + "' && saleYear ='" + cobYear.getValue() + "';");
					}
				}

			}
		});

	}

}
