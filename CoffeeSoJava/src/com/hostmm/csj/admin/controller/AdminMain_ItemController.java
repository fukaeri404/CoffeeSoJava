package com.hostmm.csj.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.ItemDAO;
import com.hostmm.csj.staff.model.Staff;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMain_ItemController implements Initializable{

	@FXML
	private TableColumn<Item, String> available;

	@FXML
	private TableColumn<Item, String> description;

	@FXML
	private TableColumn<Item, String> mood;

	@FXML
	private TableColumn<Item, String> name;

	@FXML
	private TableColumn<Item, Double> price;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private TableView<Item> tvItem;

	private Item item = Item.getItemInstance();
	private ItemDAO itemDAO = new ItemDAO();

	@FXML
	void processAdd(ActionEvent event) {
		try {
			VBox register = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/item/view/ItemRegisterView.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(register);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void processDelete(ActionEvent event) {

	}

	@FXML
	void processEdit(ActionEvent event) {
		Item selectedItem = tvItem.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			this.item.setId(selectedItem.getId());
			this.item.setName(selectedItem.getName());
			this.item.setPrice(selectedItem.getPrice());
			this.item.setAvailable(selectedItem.getAvailable());
			this.item.setMood(selectedItem.getMood());
			this.item.setDescription(selectedItem.getDescription());
			this.item.setStatus(selectedItem.getStatus());
			this.item.setImageName(selectedItem.getImageName());

			try {
				VBox register = FXMLLoader
						.load(getClass().getResource("/com/hostmm/csj/item/view/ItemRegisterView.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(register);
				stage.setScene(scene);
				stage.setOnCloseRequest((e) -> {
					this.item.setStatus("");
				});
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	void processRefresh(ActionEvent event) {
		showTable("select * from coffee");
	}
	
	private void showTable(String sql) {
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		mood.setCellValueFactory(new PropertyValueFactory<>("mood"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
		available.setCellValueFactory(new PropertyValueFactory<>("available"));
		
		ObservableList<Item> itemList = this.itemDAO.getItemList(sql);
		tvItem.setItems(itemList);
//		tfSearch.textProperty().addListener(
//				(observable, oldValue, newValue) -> tvUser.setItems(filterList(userList, newValue.toLowerCase())));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable("select * from coffee");
	}

}
