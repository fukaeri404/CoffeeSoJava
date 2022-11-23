package com.hostmm.csj.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.ItemDAO;
import com.hostmm.csj.staff.model.Staff;
import com.hostmm.csj.utility.notification.MyNotification;
import com.hostmm.csj.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class AdminMain_ItemController implements Initializable {

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
	private MyNotification myNoti = new MyNotification();

	@FXML
	void processAdd(ActionEvent event) {
		try {
			VBox register = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/item/view/ItemRegisterView.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(register);
			stage.setScene(scene);
			stage.show();
			stage.showingProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (!newValue) {
						refresh();
					}
				}

			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void processDelete(ActionEvent event) {

		Item selectedItem = tvItem.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure to delete?");
			Optional<ButtonType> action = alert.showAndWait();
			if (action.get() == ButtonType.OK) {
				int rowEffected = this.itemDAO.deleteItemByName(selectedItem);
				if (rowEffected > 0) {
					String messageTitle = "Deleted";
					String message = "successfully deleted User";
					MyNotificationType notitype = MyNotificationType.SUCCESS;
					Duration dismissTime = Duration.seconds(3);
					myNoti.getNotification(messageTitle, message, notitype, dismissTime);
					refresh();
				}
			}

		}

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
				stage.show();
				stage.showingProperty().addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (!newValue) {
							item.setStatus("");
							refresh();
						}
					}

				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	void processRefresh(ActionEvent event) {
		refresh();
	}

	private void refresh() {
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
		tfSearch.textProperty().addListener(
				(observable, oldValue, newValue) -> tvItem.setItems(filterList(itemList, newValue.toLowerCase())));

	}

	private ObservableList<Item> filterList(List<Item> list, String searchText) {
		List<Item> filteredList = new ArrayList<>();

		for (Item item : list) {
			if (searchItem(item, searchText)) {
				filteredList.add(item);
			}
		}
		return FXCollections.observableList(filteredList);
	}

	private boolean searchItem(Item item, String searchText) {
		return item.getName().toLowerCase().contains(searchText);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable("select * from coffee");
	}

}
