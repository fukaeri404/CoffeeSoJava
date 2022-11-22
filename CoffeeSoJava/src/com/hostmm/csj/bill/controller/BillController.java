package com.hostmm.csj.bill.controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.hostmm.csj.bill.model.Bill;
import com.hostmm.csj.bill.model.BillDAO;
import com.hostmm.csj.item.model.Item;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillController implements Initializable {

	@FXML
	private DatePicker dpSaleDate;

	@FXML
	private TableColumn<Bill, String> name;

	@FXML
	private TableColumn<Bill, Integer> quantity;

	@FXML
	private TableColumn<Bill, String> saleMonth;

	@FXML
	private TableColumn<Bill, String> saleDate;

	@FXML
	private TableColumn<Bill, String> saleYear;

	@FXML
	private TableColumn<Bill, String> totalPrice;

	@FXML
	private Label lblTotalSale;

	@FXML
	private TableView<Bill> tvBill;

	private BillDAO billDAO = new BillDAO();

	private void showTable(String sql) {
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
		saleMonth.setCellValueFactory(new PropertyValueFactory<>("saleMonth"));
		saleDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
		saleYear.setCellValueFactory(new PropertyValueFactory<>("saleYear"));
		ObservableList<Bill> billList = this.billDAO.getBillList(sql);
		tvBill.setItems(billList);
		lblTotalSale.setText("$ " + BillDAO.getTotalPrice());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dpSaleDate.setValue(LocalDate.now());
		showTable("select * from history where saleMonth = '" + LocalDate.now().getMonthValue() + "' && saleDate = '"
				+ LocalDate.now().getDayOfMonth() + "' && saleYear = '" + LocalDate.now().getYear() + "';");
		dpSaleDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				showTable("select * from history where saleMonth = '" + newValue.getMonthValue() + "' && saleDate = '"
						+ newValue.getDayOfMonth() + "' && saleYear = '" + newValue.getYear() + "';");
			}
		});
	}

}
