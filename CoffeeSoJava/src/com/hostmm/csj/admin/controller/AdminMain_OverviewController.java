package com.hostmm.csj.admin.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import com.hostmm.csj.bill.model.Bill;
import com.hostmm.csj.bill.model.BillDAO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.DatePicker;

public class AdminMain_OverviewController implements Initializable {

	@FXML
	private DatePicker dpDate;

	@FXML
	private PieChart pieDate;

	@FXML
	private PieChart pieMonth;

	@FXML
	private PieChart pieYear;

	private BillDAO billDAO = new BillDAO();

	private void showPieChart(LocalDate newValue) {
		ObservableList<Bill> billDateList = billDAO.getBillList(
				"select * from history where saleMonth = '" + newValue.getMonthValue() + "' && saleDate = '"
						+ newValue.getDayOfMonth() + "' && saleYear = '" + newValue.getYear() + "';");
		ObservableList<Bill> billMonthList = billDAO.getBillList("select * from history where saleMonth = '"
				+ newValue.getMonthValue() + "' && saleYear = '" + newValue.getYear() + "';");
		ObservableList<Bill> billYearList = billDAO
				.getBillList("select * from history where saleYear = '" + newValue.getYear() + "';");

		pieDate.setData(getChartData(billDateList));

		pieMonth.setData(getChartData(billMonthList));

		pieYear.setData(getChartData(billYearList));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dpDate.setValue(LocalDate.now());
		showPieChart(LocalDate.now());

		dpDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				showPieChart(newValue);
//				showPieChart("select * from history where saleMonth = '" + newValue.getMonthValue()
//						+ "' && saleYear = '" + newValue.getYear() + "';", "month");
//				showPieChart("select * from history where saleYear = '" + newValue.getYear() + "';", "year");
			}
		});
	}

	private ObservableList<Data> getChartData(ObservableList<Bill> billList) {
		ObservableList<Data> pieDatalist = FXCollections.observableArrayList();
		Map<String, Integer> map = new HashMap<String, Integer>();
		String name = "";
		int quantity = 0;
		int totalQ = 0;
		boolean addNewItem = false;
		if (billList.size() > 0) {
			map.put(billList.get(0).getName(), billList.get(0).getQuantity());
		}
		for (Bill bill : billList) {
			for (Map.Entry<String, Integer> m : map.entrySet()) {
				if (bill.getName().equalsIgnoreCase(m.getKey())) {
					addNewItem = false;
					m.setValue(m.getValue() + bill.getQuantity());
					break;
				} else {
					addNewItem = true;
					name = bill.getName();
					quantity = bill.getQuantity();
				}
			}
			if (addNewItem) {
				map.put(name, quantity);
			}
		}

		for (Bill bill : billList) {
			totalQ += bill.getQuantity();
		}

		for (Map.Entry<String, Integer> m : map.entrySet()) {
			int percent = (m.getValue() * 100) / totalQ;
			pieDatalist.add(new PieChart.Data(m.getKey() + " (" + m.getValue() + " items)", percent));
		}

		return pieDatalist;
	}

}
