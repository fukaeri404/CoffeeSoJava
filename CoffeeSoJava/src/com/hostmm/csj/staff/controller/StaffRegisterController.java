package com.hostmm.csj.staff.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

import com.hostmm.csj.login.controller.LoginController;
import com.hostmm.csj.login.model.LoginDAO;
import com.hostmm.csj.staff.model.Staff;
import com.hostmm.csj.staff.model.StaffDAO;
import com.hostmm.csj.utility.crypto.PasswordEncoder;
import com.hostmm.csj.utility.features.CommonDAO;
import com.hostmm.csj.utility.features.CommonMethod;
import com.hostmm.csj.utility.notification.MyNotification;
import com.hostmm.csj.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXRadioButton;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StaffRegisterController implements Initializable {

	@FXML
	private ComboBox<String> cobGender;

	@FXML
	private ComboBox<String> cobRole;

	@FXML
	private DatePicker dpEmployDate;

	@FXML
	private DatePicker dpResignDate;

	@FXML
	private ImageView imgviewProfile;

	@FXML
	private TextArea taAddress;

	@FXML
	private TextField tfAge;

	@FXML
	private TextField tfName;

	@FXML
	private TextField tfPassword;

	@FXML
	private TextField tfPhone;

	@FXML
	private TextField tfSalary;

	@FXML
	private TextField tfUsername;

	@FXML
	private TextField tfUserID;

	@FXML
	private ToggleGroup toggleStatus;

	@FXML
	private JFXRadioButton rbWorking;

	@FXML
	private Label lblResignDate;

	@FXML
	private JFXRadioButton rbResign;

	private Staff staff = Staff.getStaffInstance();
	private File imageFile;
	private String currentImageName;
	private StaffDAO staffDAO = new StaffDAO();
	private CommonDAO commonDAO = new CommonDAO();
	private MyNotification myNoti = new MyNotification();

	@FXML
	void processCancel(ActionEvent event) {

	}

	@FXML
	void processSave(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		int rowEffected = 0;
		String userID = tfUserID.getText().trim();
		String username = tfUsername.getText().trim();
		String rawPassword = tfPassword.getText();
		String name = tfName.getText().trim();
		int age = Integer.parseInt(tfAge.getText());
		String gender = cobGender.getValue();
		String phone = tfPhone.getText().trim();
		String address = taAddress.getText().trim();
		String role = cobRole.getValue();
		Double salary = Double.parseDouble(tfSalary.getText());
		LocalDate employDate = dpEmployDate.getValue();
		String status = toggleStatus.getSelectedToggle().getUserData().toString();
		LocalDate resignDate = null;

		String imageName = UUID.randomUUID().toString() + ".jpg";
		File outputFile = new File("src/com/hostmm/csj/staff/profile/" + imageName);

		String encodePassword = "";

		if (resignDate == null) {
			resignDate = employDate;
		}

		if (this.imageFile == null) {
			if (this.currentImageName != null)
				imageName = this.currentImageName;
		}

		if (this.staff.getAccountHistory().isBlank()) {
			this.staff.setAccountHistory("createdBy-" + LoginDAO.getLoggedUserID() + "@" + LocalDate.now());
			encodePassword = PasswordEncoder.encodePassword(rawPassword);
			Staff staff = new Staff(userID, username, encodePassword, name, age, gender, phone, address, role, salary,
					employDate, resignDate, status, true, imageName, this.staff.getAccountHistory());
			rowEffected = staffDAO.createStaff(staff);
		} else {
			this.staff.setAccountHistory(this.staff.getAccountHistory() + ",modifiedBy-" + LoginDAO.getLoggedUserID()
					+ "@" + LocalDate.now());
			Staff staff = null;
			if (rawPassword != null) {
				encodePassword = PasswordEncoder.encodePassword(rawPassword);
				staff = new Staff(userID, username, encodePassword, name, age, gender, phone, address, role, salary,
						employDate, resignDate, status, true, imageName, this.staff.getAccountHistory());
			} else
				staff = new Staff(userID, username, this.staff.getPassword(), name, age, gender, phone, address, role,
						salary, employDate, resignDate, status, true, imageName, this.staff.getAccountHistory());

			rowEffected = staffDAO.updateStaff(staff);

		}

		if (rowEffected > 0) {
			if (this.imageFile != null) {
				imageUpload(this.imageFile, outputFile);
				if (this.staff.getAccountHistory().contains("modifiedBy")) {
					File deletedImage = new File("src/com/hostmm/csj/staff/profile/" + this.currentImageName);
					if (deletedImage != null)
						Files.delete(deletedImage.toPath());
				}
			}

			if (!this.staff.getAccountHistory().contains("modifiedBy")) {
				String message = "successfully created new User";
				MyNotificationType notitype = MyNotificationType.SUCCESS;
				Duration dismissTime = Duration.seconds(3);
				myNoti.getNotification("Created", message, notitype, dismissTime);
			} else {
				String message = "successfully modified selected User";
				MyNotificationType notitype = MyNotificationType.SUCCESS;
				Duration dismissTime = Duration.seconds(3);
				myNoti.getNotification("Modified", message, notitype, dismissTime);
			}

			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.close();

			this.staff.setAccountHistory("");

		}

	}

	/**
	 * This method handles adding data automatically to specific text field to edit
	 * existed user.
	 */
	private void existedStaff() {
		tfUserID.setText(this.staff.getUserID());
		tfUsername.setText(this.staff.getUsername());
		tfPassword.setText(null);
		tfPassword.setPromptText("Enter a password to alter old password");
		tfName.setText(this.staff.getName());
		tfAge.setText(String.valueOf(this.staff.getAge()));
		cobGender.setValue(this.staff.getGender());
		tfPhone.setText(this.staff.getPhone());
		taAddress.setText(this.staff.getAddress());
		cobRole.setValue(this.staff.getRole());
		tfSalary.setText(String.valueOf(this.staff.getSalary()));
		dpEmployDate.setValue(this.staff.getEmployDate());
		if (this.staff.getStatus().equals("working"))
			rbWorking.setSelected(true);
		else {
			rbResign.setSelected(true);
			dpResignDate.setVisible(true);
			lblResignDate.setVisible(true);
		}

		dpResignDate.setValue(this.staff.getResignDate());

		this.currentImageName = this.staff.getImageName();
		File imageFile = new File("src/com/hostmm/csj/staff/profile/" + this.currentImageName);
		if (imageFile != null) {
			Image imageProfile = new Image(imageFile.getAbsolutePath());
			imgviewProfile.setImage(imageProfile);
		}
	}

	private void clear() {

	}

	/**
	 * This method handles image getting from register and upload it to file
	 * repository.
	 * 
	 * @param inputFile  : image getting from register
	 * @param outputFile : file to write in your file repository
	 * @throws IOException
	 */
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rbWorking.setUserData("working");
		rbResign.setUserData("resign");
		dpResignDate.setVisible(false);
		lblResignDate.setVisible(false);

		ObservableList<String> role = FXCollections.observableArrayList("admin", "cashier");
		ObservableList<String> gender = FXCollections.observableArrayList("male", "female");

		cobGender.setItems(gender);
		cobRole.setItems(role);

		if (this.staff.getAccountHistory().isBlank()) {
			cobRole.setDisable(false);
			tfUserID.setText(CommonMethod.generateID("staff", commonDAO.getRowCount("staff")));
		} else {
			cobRole.setDisable(true);
			existedStaff();
		}

		/* listen on toggle status and controls resign date visible */
		toggleStatus.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (toggleStatus.getSelectedToggle() != null) {
					if (newValue.getUserData().equals("working")) {
						dpResignDate.setVisible(false);
						lblResignDate.setVisible(false);
					} else {
						dpResignDate.setVisible(true);
						lblResignDate.setVisible(true);
					}

				}
			}

		});

		imgviewProfile.setOnMouseClicked((e) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
			File imageFile = fileChooser.showOpenDialog(null);
			if (imageFile != null) {
				Image image = new Image(imageFile.getAbsolutePath());
				imgviewProfile.setImage(image);
				this.imageFile = imageFile;
			}

		});
		
	}

}
