package com.hostmm.csj.item.card.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.OrderedItem;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ItemCardController implements Initializable {

	@FXML
	private ImageView imgviewCoffee;

	@FXML
	private Label lblCoffeeName;

	@FXML
	private Label lblDescription;

	@FXML
	private ToggleGroup mood;

	@FXML
	private JFXRadioButton rbCold;

	@FXML
	private JFXRadioButton rbHot;


	@FXML
	private Label lblPrice;

	private Item item = Item.getItemInstance();
	

	@FXML
	void processAddtoBill(ActionEvent event) {
		
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rbCold.setUserData("cold");
		rbHot.setUserData("hot");

		lblCoffeeName.setText(this.item.getName().toUpperCase());
		lblDescription.setText(this.item.getDescription());
		lblPrice.setText("$ " + String.valueOf(this.item.getPrice()));
		if (this.item.getMood().equalsIgnoreCase("both")) {
			rbHot.setVisible(true);
			rbCold.setVisible(true);
		} else if (this.item.getMood().equalsIgnoreCase("hot")) {
			rbHot.setVisible(true);
			rbCold.setVisible(false);
		} else {
			rbHot.setVisible(false);
			rbCold.setVisible(true);
		}
		File imageFile = new File("src/com/hostmm/csj/item/image/" + this.item.getImageName());
		if (imageFile != null) {
			Image imageProfile = new Image(imageFile.getAbsolutePath());
			imgviewCoffee.setImage(imageProfile);
		}
	}

}
