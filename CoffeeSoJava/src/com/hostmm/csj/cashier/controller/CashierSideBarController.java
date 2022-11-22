package com.hostmm.csj.cashier.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierSideBarController {

    @FXML
    void processHistory(ActionEvent event) {
    	try {
			VBox root = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/bill/view/BillView.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setResizable(false);
			stage.setTitle("Sale History");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/hostmm/csj/style/image/history (2).png")));
			stage.setScene(scene);
			stage.show();
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
			Stage stage = (Stage)((JFXButton)event.getSource()).getScene().getWindow();
			stage.hide();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

}
