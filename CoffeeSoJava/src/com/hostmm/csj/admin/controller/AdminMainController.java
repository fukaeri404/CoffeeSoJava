package com.hostmm.csj.admin.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/login/view/LoginView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
			stage.hide();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	void processOverview(ActionEvent event) {
		
		try {
			AnchorPane apOverview = FXMLLoader
					.load(getClass().getResource("/com/hostmm/csj/admin/view/AdminMainView_Overview.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(apOverview);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void processHistory(ActionEvent event) {
		dynamicStackPane.getChildren().clear();
		try {
			VBox vbHistory = FXMLLoader
					.load(getClass().getResource("/com/hostmm/csj/admin/view/AdminMainView_History.fxml"));
			dynamicStackPane.getChildren().add(vbHistory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
