package com.hostmm.csj.cashier.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.ItemDAO;
import com.hostmm.csj.item.model.OrderedItem;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CashierMainController implements Initializable {

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private Pane paneCoffee;

	@FXML
	private FlowPane flowPane;

	@FXML
	private FlowPane billFlowPane;

	private ItemDAO itemDAO = new ItemDAO();
	private Item item = Item.getItemInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		drawer.open();
		setSidePane();
		addItemCard();
		paneCoffee.setOnMouseClicked((e)->{
			
		});
	}

	private void addItemCard() {
		ObservableList<Item> itemList = itemDAO.getItemList("select * from coffee");
		for (Item item : itemList) {
			this.item.setName(item.getName());
			this.item.setMood(item.getMood());
			this.item.setPrice(item.getPrice());
			this.item.setDescription(item.getDescription());
			this.item.setImageName(item.getImageName());
			try {
				AnchorPane card = FXMLLoader
						.load(getClass().getResource("/com/hostmm/csj/item/card/view/ItemCardView.fxml"));
				flowPane.getChildren().add(card);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void setSidePane() {

		try {
			VBox sidepane = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/cashier/view/CashierSideBar.fxml"));
			drawer.setSidePane(sidepane);

			HamburgerBackArrowBasicTransition task = new HamburgerBackArrowBasicTransition(hamburger);
			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				task.setRate(task.getRate() * -1);
				task.play();
				if (drawer.isShown())
					drawer.close();
				else
					drawer.open();
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
