package com.hostmm.csj.item.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hostmm.csj.cashier.controller.CashierMainController;
import com.hostmm.csj.item.card.controller.ItemCardController;
import com.hostmm.csj.item.model.OrderedItem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class OrderedItemController implements Initializable {

	@FXML
	private Label lblCoffeeName;

	@FXML
	private Label lblQuantity;

	@FXML
	private Label lblTotalPrice;

	private OrderedItem orderedItem = OrderedItem.getInstance();

	private double subT;

	@FXML
	void processRemove(ActionEvent event) {
		String[] split = String.valueOf(ItemCardController.getSubTotal().get()).split(" ");
		subT = Double.parseDouble(split[1]);
		CashierMainController.getBillFlowPane().getChildren().clear();
		for (OrderedItem oi : OrderedItem.getListInstance()) {
			if (oi.getName().equalsIgnoreCase(lblCoffeeName.getText())) {
				subT -= oi.getTotalPrice();
				ItemCardController.setSubTotal(subT);
				OrderedItem.getListInstance().remove(oi);
				break;
			}
		}
		for (OrderedItem oi : OrderedItem.getListInstance()) {
			this.orderedItem.setName(oi.getName());
			this.orderedItem.setQuantity(oi.getQuantity());
			this.orderedItem.setTotalPrice(oi.getTotalPrice());
			try {
				AnchorPane root = FXMLLoader
						.load(getClass().getResource("/com/hostmm/csj/item/view/OrderedItemView.fxml"));
				CashierMainController.getBillFlowPane().getChildren().add(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblCoffeeName.setText(this.orderedItem.getName());
		lblQuantity.setText("x " + this.orderedItem.getQuantity());
		lblTotalPrice.setText("$ " + String.valueOf(this.orderedItem.getTotalPrice()));
	}

}
