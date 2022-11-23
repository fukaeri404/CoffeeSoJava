package com.hostmm.csj.login.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hostmm.csj.login.model.LoginDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.PauseTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

	@FXML
	private ImageView imgviewShowPassword;

	@FXML
	private Label lblError;

	@FXML
	private PasswordField pfPassword;

	@FXML
	private TextField tfPassword;

	@FXML
	private TextField tfUsername;

	@FXML
	private JFXComboBox<String> cobRole;

	@FXML
	private JFXButton btnLogin;

	private LoginDAO loginDAO = new LoginDAO();

	@FXML
	void processLogin(ActionEvent event) throws SQLException {

		String username = tfUsername.getText().trim();
		String password = pfPassword.getText();
		String role = cobRole.getValue();
		boolean signinOk = loginDAO.isCredentialsValid(username, password, role);
		if (signinOk) {
			try {
				if (role.equals("admin")) {
					BorderPane root = FXMLLoader
							.load(getClass().getResource("/com/hostmm/csj/admin/view/AdminMainView.fxml"));
					Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
					stage.hide();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(
							getClass().getResource("/com/hostmm/csj/style/css/adminMainStyle.css").toExternalForm());
					stage.setScene(scene);
					stage.show();
				}else {
					AnchorPane root = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/cashier/view/CashierMainView.fxml"));
					Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
					stage.hide();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(
							getClass().getResource("/com/hostmm/csj/style/css/cashierMainStyle.css").toExternalForm());
					stage.setScene(scene);
					stage.show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			lblError.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished((e) -> lblError.setVisible(false));
			visiblePause.play();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> role = FXCollections.observableArrayList("admin", "cashier");
		cobRole.setItems(role);
		lblError.setVisible(false);
		tfPassword.setVisible(false);

		BooleanBinding booleanBind = tfUsername.textProperty().isEmpty().or(pfPassword.textProperty().isEmpty())
				.or(cobRole.valueProperty().isNull());
		btnLogin.disableProperty().bind(booleanBind);

		imgviewShowPassword.setOnMouseClicked((e) -> {
			if (tfPassword.isVisible()) {
				imgviewShowPassword.setImage(
						new Image(getClass().getResourceAsStream("/com/hostmm/csj/style/image/eyes (2).png")));
				tfPassword.setVisible(false);
				pfPassword.setVisible(true);
			}

			else {
				imgviewShowPassword.setImage(
						new Image(getClass().getResourceAsStream("/com/hostmm/csj/style/image/eyes (1).png")));
				tfPassword.setVisible(true);
				pfPassword.setVisible(false);
			}
		});

		pfPassword.setOnKeyReleased((e) -> {
			tfPassword.setText(pfPassword.getText());
		});
		
		tfPassword.setOnKeyReleased((e)->{
			pfPassword.setText(tfPassword.getText());
		});
	}

}
