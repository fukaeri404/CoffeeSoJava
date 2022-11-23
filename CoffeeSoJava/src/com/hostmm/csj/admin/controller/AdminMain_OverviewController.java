package com.hostmm.csj.admin.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import com.hostmm.csj.bill.model.Bill;
import com.hostmm.csj.bill.model.BillDAO;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AdminMain_OverviewController implements Initializable {

	@FXML
	private DatePicker dpDate;

	@FXML
	private PieChart pieDate;

	@FXML
	private PieChart pieMonth;

	@FXML
	private HBox mobileHBox;

	@FXML
	private PieChart pieYear;

	@FXML
	private Button btnLeft;

	@FXML
	private VBox vbMonth;

	@FXML
	private VBox vbDate;

	@FXML
	private VBox vbYear;

	@FXML
	private Button btnRight;

	@FXML
	private Label lblDayStatus;

	@FXML
	private Label lblMonthStatus;

	@FXML
	private Label lblYearStatus;

	private BillDAO billDAO = new BillDAO();
	TranslateTransition translateMonth;
	TranslateTransition translateDate;
	TranslateTransition translateYear;
	String current = "date";

	@FXML
	void processLeft(ActionEvent event) {
		if (current == "date") { // correct
			translateMonth.setByX(750);
			translateDate.setByX(750);
			btnLeft.setDisable(true);
			current = "month";
			translateDate.play();
			translateMonth.play();
		} else if (current == "year") {
			translateYear.setByX(750);
			translateDate.setByX(750);
			btnRight.setDisable(false);
			btnLeft.setDisable(true);
			current = "date";
			translateDate.play();
			translateYear.play();
			PauseTransition disablePause = new PauseTransition(Duration.seconds(1));
			disablePause.setOnFinished((e) -> btnLeft.setDisable(false));
			disablePause.play();
		}
		
	}

	@FXML
	void processRight(ActionEvent event) {
		if (current == "date") {
			translateYear.setByX(-750);
			translateDate.setByX(-750);
			btnRight.setDisable(true);
			current = "year";
			translateDate.play();
			translateYear.play();
		} else if (current == "month") {
			translateMonth.setByX(-750);
			translateDate.setByX(-750);
			btnLeft.setDisable(false);
			btnRight.setDisable(true);
			current = "date";
			translateMonth.play();
			translateDate.play();
			PauseTransition disablePause = new PauseTransition(Duration.seconds(1));
			disablePause.setOnFinished((e) -> btnRight.setDisable(false));
			disablePause.play();
		}
	}

	private void showPieChart(LocalDate newValue) {
		ObservableList<Bill> billDateList = billDAO.getBillList(
				"select * from history where saleMonth = '" + newValue.getMonthValue() + "' && saleDate = '"
						+ newValue.getDayOfMonth() + "' && saleYear = '" + newValue.getYear() + "';");
		ObservableList<Bill> billMonthList = billDAO.getBillList("select * from history where saleMonth = '"
				+ newValue.getMonthValue() + "' && saleYear = '" + newValue.getYear() + "';");
		ObservableList<Bill> billYearList = billDAO
				.getBillList("select * from history where saleYear = '" + newValue.getYear() + "';");

		if (billDateList.size() == 0)
			lblDayStatus.setVisible(true);
		else
			lblDayStatus.setVisible(false);
		if (billMonthList.size() == 0)
			lblMonthStatus.setVisible(true);
		else
			lblMonthStatus.setVisible(false);
		if (billYearList.size() == 0)
			lblYearStatus.setVisible(true);
		else
			lblYearStatus.setVisible(false);

		pieDate.setData(getChartData(billDateList));
		pieMonth.setData(getChartData(billMonthList));
		pieYear.setData(getChartData(billYearList));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dpDate.setValue(LocalDate.now());
		showPieChart(LocalDate.now());

		translateMonth = new TranslateTransition();
		translateDate = new TranslateTransition();
		translateYear = new TranslateTransition();
		translateMonth.setDuration(Duration.millis(1000));
		translateDate.setDuration(Duration.millis(1000));
		translateYear.setDuration(Duration.millis(1000));
		translateDate.setNode(vbDate);
		translateMonth.setNode(vbMonth);
		translateYear.setNode(vbYear);

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
