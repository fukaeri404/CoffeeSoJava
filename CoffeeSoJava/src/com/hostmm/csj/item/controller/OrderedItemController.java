package com.hostmm.csj.item.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hostmm.csj.item.model.OrderedItem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OrderedItemController implements Initializable {

	@FXML
	private Label lblCoffeeName;

	@FXML
	private Label lblQuantity;

	@FXML
	private Label lblTotalAmount;

	@FXML
	private TextField tfQuantity;
	
	private int quantity;

	@FXML
	void processDecrease(ActionEvent event) {
		if (quantity > 0) {
			quantity -= 1;
			tfQuantity.setText(String.valueOf(quantity));
		}
	}

	@FXML
	void processIncrease(ActionEvent event) {
		quantity += 1;
		tfQuantity.setText(String.valueOf(quantity));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quantity = Integer.parseInt(tfQuantity.getText());
		
//		lblCoffeeName.setText(this.orderedItem.getName());
//		lblQuantity.setText("x " + this.orderedItem.getQuantity());
//		lblTotalAmount.setText(String.valueOf(this.orderedItem.getTotalAmount()));
	}

}
