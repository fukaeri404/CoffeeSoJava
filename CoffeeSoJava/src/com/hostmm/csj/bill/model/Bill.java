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
	private SimpleStringProperty saleDate;

	public Bill(String name, int quantity, double totalPrice, LocalDate saleDate) {
		super();
		this.name = new SimpleStringProperty(name);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
		this.saleDate = new SimpleStringProperty(saleDate.toString());
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

	public LocalDate getSaleDate() {
		LocalDate date = LocalDate.parse(this.saleDate.get());
		return date;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = new SimpleStringProperty(saleDate.toString());
	}

}
