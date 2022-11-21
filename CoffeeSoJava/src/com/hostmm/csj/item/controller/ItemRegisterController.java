package com.hostmm.csj.item.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.UUID;

import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.ItemDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ItemRegisterController implements Initializable {

	@FXML
	private ComboBox<String> cobAvailable;

	@FXML
	private ComboBox<String> cobMood;

	@FXML
	private ImageView imgviewCoffee;

	@FXML
	private TextArea taDescription;

	@FXML
	private TextField tfName;

	@FXML
	private TextField tfPrice;
	private Item item = Item.getItemInstance();
	private File imageFile;
	private String currentImageName;
	private ItemDAO itemDAO = new ItemDAO();

	@FXML
	void processSave(ActionEvent event) throws IOException {
		int rowEffected = 0;
		String name = tfName.getText().trim();
		double price = Double.parseDouble(tfPrice.getText().trim());
		String mood = cobMood.getValue();
		String available = cobAvailable.getValue();
		String description = taDescription.getText();
		String imageName = UUID.randomUUID().toString() + ".jpg";
		File outputFile = new File("src/com/hostmm/csj/item/image/" + imageName);
		
		if (this.imageFile == null) {
			if (this.currentImageName != null)
				imageName = this.currentImageName;
		}
		
		if(this.item.getStatus().isBlank()) {
			this.item.setStatus("created");
			Item item = new Item(name, price, mood, available, description, imageName, this.item.getStatus());
			rowEffected = itemDAO.createItem(item);
		}else {
			this.item.setStatus("modified");
			Item item = new Item(this.item.getId(), name, price, mood, available, description, imageName, this.item.getStatus());
			rowEffected = itemDAO.updateItem(item);
		}
		
		if(rowEffected > 0) {
			if (this.imageFile != null) {
				imageUpload(this.imageFile, outputFile);
				if (this.item.getStatus().equalsIgnoreCase("modified")) {
					File deletedImage = new File("src/com/hostmm/csj/item/image/" + this.currentImageName);
					if (deletedImage != null)
						Files.delete(deletedImage.toPath());
				}
			}
			
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.close();

			this.item.setStatus("");
		}
	}
	
	public void imageUpload(File inputFile, File outputFile) throws IOException {

		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(this.imageFile));
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
			byte[] byteBuffer = new byte[4000];
			int numOfByte = 0;
			while ((numOfByte = bufferedInputStream.read(byteBuffer)) != -1) {
				bufferedOutputStream.write(byteBuffer, 0, numOfByte);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void existedItem() {
		tfName.setText(this.item.getName());
		tfPrice.setText(String.valueOf(this.item.getPrice()));
		cobMood.setValue(this.item.getMood());
		cobAvailable.setValue(this.item.getAvailable());
		taDescription.setText(this.item.getDescription());

		this.currentImageName = this.item.getImageName();
		File imageFile = new File("src/com/hostmm/csj/item/image/" + this.currentImageName);
		if (imageFile != null) {
			Image imageProfile = new Image(imageFile.getAbsolutePath());
			imgviewCoffee.setImage(imageProfile);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> available = FXCollections.observableArrayList("available", " currently not available");
		ObservableList<String> mood = FXCollections.observableArrayList("hot", "cold", "both");

		cobAvailable.setItems(available);
		cobMood.setItems(mood);

		if (!this.item.getStatus().isBlank())
			existedItem();

		imgviewCoffee.setOnMouseClicked((e) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
			File imageFile = fileChooser.showOpenDialog(null);
			if (imageFile != null) {
				Image image = new Image(imageFile.getAbsolutePath());
				imgviewCoffee.setImage(image);
				this.imageFile = imageFile;
			}
		});
	}

}
