package com.hostmm.csj.item.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderedItem {

	private final static OrderedItem INSTANCE = new OrderedItem();

	public static OrderedItem getInstance() {
		return INSTANCE;
	}
	
	private final static ObservableList<OrderedItem> LIST_InSTANCE = FXCollections.observableArrayList();
	
	public static ObservableList<OrderedItem> getListInstance() {
		return LIST_InSTANCE;
	}

	private SimpleStringProperty name;
	private SimpleStringProperty mood;
	private SimpleIntegerProperty quantity;
	private SimpleDoubleProperty totalAmount;

	public OrderedItem() {
		// TODO Auto-generated constructor stub
	}

	public OrderedItem(String name, String mood, int quantity, double totalAmount) {
		super();
		this.name = new SimpleStringProperty(name);
		this.mood = new SimpleStringProperty(mood);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.totalAmount = new SimpleDoubleProperty(totalAmount);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public String getMood() {
		return mood.get();
	}

	public void setMood(String mood) {
		this.mood = new SimpleStringProperty(mood);
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	public double getTotalAmount() {
		return totalAmount.get();
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = new SimpleDoubleProperty(totalAmount);
	}

}
