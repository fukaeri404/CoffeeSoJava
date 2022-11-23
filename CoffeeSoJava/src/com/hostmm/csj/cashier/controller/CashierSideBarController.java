package com.hostmm.csj.cashier.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierSideBarController implements Initializable {

	@FXML
	private JFXToggleButton jtTheme;

	@FXML
	void processHistory(ActionEvent event) {
		try {
			VBox root = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/bill/view/BillView.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setResizable(false);
			stage.setTitle("Sale History");
			stage.getIcons()
					.add(new Image(getClass().getResourceAsStream("/com/hostmm/csj/style/image/history (2).png")));
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
			Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
			stage.hide();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		jtTheme.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Stage stage = (Stage) jtTheme.getScene().getWindow();
				Scene scene = stage.getScene();
				if (newValue.equals(true)) {
					scene.getStylesheets().clear();
					scene.getStylesheets().add(getClass()
							.getResource("/com/hostmm/csj/style/css/cashierMainStyleCoffee.css").toExternalForm());
				} else {
					scene.getStylesheets().clear();
					scene.getStylesheets().add(
							getClass().getResource("/com/hostmm/csj/style/css/cashierMainStyle.css").toExternalForm());
				}
			}
		});
	}

}
