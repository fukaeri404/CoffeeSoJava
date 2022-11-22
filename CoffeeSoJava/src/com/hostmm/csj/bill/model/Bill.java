package com.hostmm.csj.bill.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bill {

	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty quantity;
	private SimpleDoubleProperty totalPrice;

	private SimpleStringProperty saleMonth;
	private SimpleStringProperty saleDate;
	private SimpleStringProperty saleYear;

	public Bill(String name, int quantity, double totalPrice, String saleMonth, String saleDate, String saleYear) {
		super();
		this.name = new SimpleStringProperty(name);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
		this.saleMonth = new SimpleStringProperty(saleMonth);
		this.saleDate = new SimpleStringProperty(saleDate);
		this.saleYear = new SimpleStringProperty(saleYear);
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

}
