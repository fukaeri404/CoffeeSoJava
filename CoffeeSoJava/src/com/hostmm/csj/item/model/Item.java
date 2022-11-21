package com.hostmm.csj.item.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

	private final static Item ITEM_INSTANCE = new Item();
	
	public static Item getItemInstance() {
		return ITEM_INSTANCE;
	}

	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleDoubleProperty price;
	private SimpleStringProperty mood;
	private SimpleStringProperty available;
	private SimpleStringProperty description;
	private SimpleStringProperty imageName;
	private SimpleStringProperty status;

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public Item(String name, double price, String mood, String available, String description, String imageName,
			String status) {
		super();
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
		this.mood = new SimpleStringProperty(mood);
		this.available = new SimpleStringProperty(available);
		this.description = new SimpleStringProperty(description);
		this.imageName = new SimpleStringProperty(imageName);
		this.status = new SimpleStringProperty(status);
	}

	public Item(int id, String name, double price, String mood, String available, String description, String imageName,
			String status) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.mood = new SimpleStringProperty(mood);
		this.price = new SimpleDoubleProperty(price);
		this.available = new SimpleStringProperty(available);
		this.description = new SimpleStringProperty(description);
		this.imageName = new SimpleStringProperty(imageName);
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

	public double getPrice() {
		return price.get();
	}

	public void setPrice(double price) {
		this.price = new SimpleDoubleProperty(price);
	}

	public String getAvailable() {
		return available.get();
	}

	public void setAvailable(String available) {
		this.available = new SimpleStringProperty(available);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}

	public String getMood() {
		return mood.get();
	}

	public void setMood(String mood) {
		this.mood = new SimpleStringProperty(mood);
	}

	public String getImageName() {
		return imageName.get();
	}

	public void setImageName(String imageName) {
		this.imageName = new SimpleStringProperty(imageName);
	}

	public String getStatus() {
		if(this.status==null)
			this.status = new SimpleStringProperty("");
		return status.get();
	}

	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}

}
