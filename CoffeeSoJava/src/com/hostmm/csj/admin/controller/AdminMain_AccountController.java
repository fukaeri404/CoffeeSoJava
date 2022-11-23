package com.hostmm.csj.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.hostmm.csj.staff.model.Staff;
import com.hostmm.csj.staff.model.StaffDAO;
import com.hostmm.csj.utility.notification.MyNotification;
import com.hostmm.csj.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXComboBox;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminMain_AccountController implements Initializable {

	@FXML
	private TableColumn<Staff, String> address;

	@FXML
	private TableColumn<Staff, Integer> age;

	@FXML
	private JFXComboBox<String> cobRole;

	@FXML
	private JFXComboBox<String> cobStatus;

	@FXML
	private TableColumn<Staff, Date> employDate;

	@FXML
	private TableColumn<Staff, String> gender;

	@FXML
	private TableColumn<Staff, String> userID;

	@FXML
	private TableColumn<Staff, String> name;

	@FXML
	private TableColumn<Staff, String> phone;

	@FXML
	private TableColumn<Staff, Date> resignDate;

	@FXML
	private TableColumn<Staff, String> role;

	@FXML
	private TableColumn<Staff, String> salary;

	@FXML
	private TableColumn<Staff, String> status;

	@FXML
	private TableColumn<Staff, String> username;

	@FXML
	private TableView<Staff> tvStaff;

	private Staff staff = Staff.getStaffInstance();
	private StaffDAO staffDAO = new StaffDAO();
	private MyNotification myNoti = new MyNotification();

	@FXML
	void processAdd(ActionEvent event) {
		try {
			AnchorPane register = FXMLLoader
					.load(getClass().getResource("/com/hostmm/csj/staff/view/StaffRegisterView.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(register);
			stage.setScene(scene);
			stage.show();
			stage.showingProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
						Boolean newValue) {
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

		Staff selectedStaff = tvStaff.getSelectionModel().getSelectedItem();
		if (selectedStaff != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure to delete?");
			Optional<ButtonType> action = alert.showAndWait();
			if (action.get() == ButtonType.OK) {
				int rowEffected = this.staffDAO.deleteStaffByID(selectedStaff);
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
		Staff selecedStaff = tvStaff.getSelectionModel().getSelectedItem();
		if (selecedStaff != null) {
			this.staff.setUserID(selecedStaff.getUserID());
			this.staff.setUsername(selecedStaff.getUsername());
			this.staff.setPassword(selecedStaff.getPassword());
			this.staff.setName(selecedStaff.getName());
			this.staff.setAge(selecedStaff.getAge());
			this.staff.setGender(selecedStaff.getGender());
			this.staff.setPhone(selecedStaff.getPhone());
			this.staff.setAddress(selecedStaff.getAddress());
			this.staff.setRole(selecedStaff.getRole());
			this.staff.setSalary(selecedStaff.getSalary());
			this.staff.setEmployDate(selecedStaff.getEmployDate());
			this.staff.setStatus(selecedStaff.getStatus());
			this.staff.setResignDate(selecedStaff.getResignDate());
			this.staff.setAccountHistory(selecedStaff.getAccountHistory());
			this.staff.setImageName(selecedStaff.getImageName());

			try {
				AnchorPane register = FXMLLoader
						.load(getClass().getResource("/com/hostmm/csj/staff/view/StaffRegisterView.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(register);
				stage.setScene(scene);
				stage.show();
				stage.showingProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (!newValue) {
							refresh();
							staff.setAccountHistory("");
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
		showTable("select * from staff where active = true;");
		cobRole.setValue("");
		cobRole.setPromptText("Choose Role");
		cobStatus.setValue("");
		cobStatus.setPromptText("Choose Status");
//		cobStatus.setValue(null);
	}

	private void showTable(String sql) {
		userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		age.setCellValueFactory(new PropertyValueFactory<>("age"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		role.setCellValueFactory(new PropertyValueFactory<>("role"));
		salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		employDate.setCellValueFactory(new PropertyValueFactory<>("employDate"));
		resignDate.setCellValueFactory(new PropertyValueFactory<>("resignDate"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		ObservableList<Staff> staffList = this.staffDAO.getStaffList(sql);
		tvStaff.setItems(staffList);
//		tfSearch.textProperty().addListener(
//				(observable, oldValue, newValue) -> tvUser.setItems(filterList(userList, newValue.toLowerCase())));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> role = FXCollections.observableArrayList("admin", "cashier");
		ObservableList<String> status = FXCollections.observableArrayList("working", "resign");

		cobRole.setItems(role);
		cobStatus.setItems(status);
		showTable("select * from staff where active = true;");

		cobRole.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!cobRole.getValue().isBlank()) {
					if (cobStatus.getValue() != null) {
						if (cobStatus.getValue().isBlank()) {
							showTable("select * from staff where role = '" + newValue
									+ "' && status = 'working'|'resign' && active = true;");
						} else {
							showTable("select * from staff where role = '" + newValue + "' && status = '"
									+ cobStatus.getValue() + "' && active = true;");
						}
					} else
						showTable("select * from staff where role = '" + newValue
								+ "' && status = 'working'|'resign' && active = true;");
				}
			}
		});

		cobStatus.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!cobStatus.getValue().isBlank()) {
					if (cobRole.getValue() != null) {
						if (cobRole.getValue().isBlank()) {
							showTable("select * from staff where role = 'admin'|'cashier' && status = '" + newValue
									+ "' && active = true;");
						} else {
							showTable("select * from staff where role = '" + cobRole.getValue() + "' && status = '"
									+ newValue + "' && active = true;");
						}
					} else
						showTable("select * from staff where role = 'admin'|'cashier' && status = '" + newValue
								+ "' && active = true;");

					if (newValue.equals("resign"))
						resignDate.setVisible(true);
					else
						resignDate.setVisible(false);
				}
			}
		});

	}

}
