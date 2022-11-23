package com.hostmm.csj.bill.controller;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.hostmm.csj.bill.model.Bill;
import com.hostmm.csj.bill.model.BillDAO;
import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.ItemDAO;
import com.hostmm.csj.staff.model.Staff;
import com.hostmm.csj.staff.model.StaffDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BillRegisterController implements Initializable {

	@FXML
	private ComboBox<String> cobName;

	@FXML
	private ComboBox<String> cobCashier;

	@FXML
	private TextField tfQuantity;

	@FXML
	private TextField tfSaleDate;

	@FXML
	private TextField tfSaleMonth;

	@FXML
	private TextField tfSaleYear;

	@FXML
	private TextField tfTotalPrice;

	private StaffDAO staffDAO = new StaffDAO();
	private BillDAO billDAO = new BillDAO();
	private ItemDAO itemDAO = new ItemDAO();
	private Bill bill = Bill.getBillinstance();

	@FXML
	void processCancel(ActionEvent event) {

	}

	@FXML
	void processSave(ActionEvent event) {
		int rowEffected = 0;
		String name = cobName.getValue();
		int quantity = Integer.parseInt(tfQuantity.getText());
		double totalPrice = Double.parseDouble(tfTotalPrice.getText());
		String saleMonth = tfSaleMonth.getText();
		String saleDate = tfSaleDate.getText();
		String saleYear = tfSaleYear.getText();
		String cashier = cobCashier.getValue();
		LocalTime saleTime = null;
		if (this.bill.getStatus().isBlank()) {
			saleTime = LocalTime.now();
			Bill bill = new Bill(name, quantity, totalPrice, saleMonth, saleDate, saleYear, saleTime, cashier,
					"created");
			rowEffected = billDAO.createBill(bill);
		} else {
			Bill bill = new Bill(this.bill.getId(), name, quantity, totalPrice, saleMonth, saleDate, saleYear,
					this.bill.getSaleTime(), cashier, "modified");
			rowEffected = billDAO.updateBill(bill);
		}

		if (rowEffected > 0) {
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.close();
			this.bill.setStatus("");
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Item> itemList = itemDAO.getItemList("select * from  coffee");
		ObservableList<String> itemName = FXCollections.observableArrayList();

		ObservableList<Staff> staffList = staffDAO
				.getStaffList("select * from staff where role = 'cashier' && active = true;");
		ObservableList<String> staffsName = FXCollections.observableArrayList();
		for (Item item : itemList) {
			itemName.add(item.getName());
		}

		for (Staff staff : staffList) {
			staffsName.add(staff.getUsername());
		}

		cobName.setItems(itemName);
		cobCashier.setItems(staffsName);

		if (!this.bill.getStatus().isBlank()) {
			cobName.setValue(this.bill.getName());
			tfQuantity.setText(String.valueOf(this.bill.getQuantity()));
			tfTotalPrice.setText(String.valueOf(this.bill.getTotalPrice()));
			tfSaleMonth.setText(this.bill.getSaleMonth());
			tfSaleDate.setText(this.bill.getSaleDate());
			tfSaleYear.setText(this.bill.getSaleYear());
			cobCashier.setValue(this.bill.getCashier());
		}
	}

}
