package com.hostmm.csj.admin.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdminMainController {

	@FXML
	private StackPane dynamicStackPane;
	
	@FXML
	void processItem(ActionEvent event) {
		dynamicStackPane.getChildren().clear();
		try {
			VBox vbItem = FXMLLoader
					.load(getClass().getResource("/com/hostmm/csj/admin/view/AdminMainView_Item.fxml"));
			dynamicStackPane.getChildren().add(vbItem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	void processLogout(ActionEvent event) {
		
	}

	@FXML
	void processAccount(ActionEvent event) {
		dynamicStackPane.getChildren().clear();
		try {
			VBox vbAccount = FXMLLoader
					.load(getClass().getResource("/com/hostmm/csj/admin/view/AdminMainView_Account.fxml"));
			dynamicStackPane.getChildren().add(vbAccount);
			dynamicStackPane.getChildren().get(0).getScene().getStylesheets().add(
					getClass().getResource("/com/hostmm/csj/style/css/adminMainAccountStyle.css").toExternalForm());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
