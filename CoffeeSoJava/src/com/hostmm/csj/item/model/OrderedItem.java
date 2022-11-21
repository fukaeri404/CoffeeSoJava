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
	private SimpleIntegerProperty quantity;
	private SimpleDoubleProperty totalPrice;

	public OrderedItem() {
		// TODO Auto-generated constructor stub
	}

	public OrderedItem(String name, int quantity, double totalPrice) {
		super();
		this.name = new SimpleStringProperty(name);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.totalPrice = new SimpleDoubleProperty(totalPrice);
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

}
