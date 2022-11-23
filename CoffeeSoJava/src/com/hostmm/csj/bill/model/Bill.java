package com.hostmm.csj.bill.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bill {

	private final static Bill billInstance = new Bill();

	public static Bill getBillinstance() {
		return billInstance;
	}

	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty quantity;
	private SimpleDoubleProperty totalPrice;

	private SimpleStringProperty saleMonth;
	private SimpleStringProperty saleDate;
	private SimpleStringProperty saleYear;
	private SimpleStringProperty saleTime;
	private SimpleStringProperty cashier;
	private SimpleStringProperty status;

	public Bill() {
		// TODO Auto-generated constructor stub
	}

	public Bill(int id, String name, int quantity, double totalPrice, String saleMonth, String saleDate,
			String saleYear, LocalTime saleTime, String cashier, String status) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
		this.saleMonth = new SimpleStringProperty(saleMonth);
		this.saleDate = new SimpleStringProperty(saleDate);
		this.saleYear = new SimpleStringProperty(saleYear);
		this.saleTime = new SimpleStringProperty(saleTime.toString());
		this.cashier = new SimpleStringProperty(cashier);
		this.status = new SimpleStringProperty(status);
	}

	public Bill(String name, int quantity, double totalPrice, String saleMonth, String saleDate, String saleYear,
			LocalTime saleTime, String cashier, String status) {
		super();
		this.name = new SimpleStringProperty(name);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
		this.saleMonth = new SimpleStringProperty(saleMonth);
		this.saleDate = new SimpleStringProperty(saleDate);
		this.saleYear = new SimpleStringProperty(saleYear);
		this.saleTime = new SimpleStringProperty(saleTime.toString());
		this.cashier = new SimpleStringProperty(cashier);
		this.status = new SimpleStringProperty(status);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	public double getTotalPrice() {
		return totalPrice.get();
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
	}

	public String getSaleDate() {
		return saleDate.get();
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = new SimpleStringProperty(saleDate);
	}

	public String getSaleMonth() {
		return saleMonth.get();
	}

	public void setSaleMonth(String saleMonth) {
		this.saleMonth = new SimpleStringProperty(saleMonth);
	}

	public String getSaleYear() {
		return saleYear.get();
	}

	public void setSaleYear(String saleYear) {
		this.saleYear = new SimpleStringProperty(saleYear);
	}

	public String getCashier() {
		return cashier.get();
	}

	public void setCashier(String cashier) {
		this.cashier = new SimpleStringProperty(cashier);
	}

	public LocalTime getSaleTime() {
		LocalTime time = LocalTime.parse(this.saleTime.get());
		return time;
	}

	public void setSaleTime(LocalTime saleTime) {
		this.saleTime = new SimpleStringProperty(saleTime.toString());
	}

	public String getStatus() {
		if (this.status == null) {
			this.status = new SimpleStringProperty("");
		}
		return status.get();
	}

	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}

}
