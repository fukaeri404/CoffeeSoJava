package com.hostmm.csj.staff.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Staff {

	private final static Staff Staff_Instance = new Staff();

	public static Staff getStaffInstance() {
		return Staff_Instance;
	}

	private SimpleStringProperty userID;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private SimpleStringProperty name;
	private SimpleIntegerProperty age;
	private SimpleStringProperty gender;
	private SimpleStringProperty phone;
	private SimpleStringProperty address;
	private SimpleStringProperty role;
	private SimpleDoubleProperty salary;
	private SimpleStringProperty employDate;
	private SimpleStringProperty resignDate;
	private SimpleStringProperty status;
	private SimpleBooleanProperty active;
	private SimpleStringProperty imageName;
	private SimpleStringProperty accountHistory;

	public Staff() {
		// TODO Auto-generated constructor stub
	}

	public Staff(String userID, String username, String password, String name, int age, String gender, String phone,
			String address, String role, double salary, LocalDate employDate, LocalDate resignDate, String status,
			boolean active, String imageName, String accountHistory) {
		super();
		this.userID = new SimpleStringProperty(userID);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.name = new SimpleStringProperty(name);
		this.age = new SimpleIntegerProperty(age);
		this.gender = new SimpleStringProperty(gender);
		this.phone = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
		this.role = new SimpleStringProperty(role);
		this.salary = new SimpleDoubleProperty(salary);
		this.employDate = new SimpleStringProperty(employDate.toString());
		this.resignDate = new SimpleStringProperty(resignDate.toString());
		this.status = new SimpleStringProperty(status);
		this.active = new SimpleBooleanProperty(active);
		this.imageName = new SimpleStringProperty(imageName);
		this.accountHistory = new SimpleStringProperty(accountHistory);
	}

	public Staff(String username, String password, String name, int age, String gender, String phone, String address,
			String role, double salary, LocalDate employDate, LocalDate resignDate, String status, boolean active,
			String imageName, String accountHistory) {
		super();
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.name = new SimpleStringProperty(name);
		this.age = new SimpleIntegerProperty(age);
		this.gender = new SimpleStringProperty(gender);
		this.phone = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
		this.role = new SimpleStringProperty(role);
		this.salary = new SimpleDoubleProperty(salary);
		this.employDate = new SimpleStringProperty(employDate.toString());
		this.resignDate = new SimpleStringProperty(resignDate.toString());
		this.status = new SimpleStringProperty(status);
		this.active = new SimpleBooleanProperty(active);
		this.imageName = new SimpleStringProperty(imageName);
		this.accountHistory = new SimpleStringProperty(accountHistory);
	}

	public String getUserID() {
		return userID.get();
	}

	public void setUserID(String userID) {
		this.userID = new SimpleStringProperty(userID);
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username = new SimpleStringProperty(username);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public int getAge() {
		return age.get();
	}

	public void setAge(int age) {
		this.age = new SimpleIntegerProperty(age);
	}

	public String getGender() {
		return gender.get();
	}

	public void setGender(String gender) {
		this.gender = new SimpleStringProperty(gender);
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String phone) {
		this.phone = new SimpleStringProperty(phone);
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address = new SimpleStringProperty(address);
	}

	public String getRole() {
		return role.get();
	}

	public void setRole(String role) {
		this.role = new SimpleStringProperty(role);
	}

	public double getSalary() {
		return salary.get();
	}

	public void setSalary(double salary) {
		this.salary = new SimpleDoubleProperty(salary);
	}

	public LocalDate getEmployDate() {
		LocalDate date = LocalDate.parse(employDate.get());
		return date;
	}

	public void setEmployDate(LocalDate employDate) {
		this.employDate = new SimpleStringProperty(employDate.toString());
	}

	public LocalDate getResignDate() {
		LocalDate date = LocalDate.parse(resignDate.get());
		return date;
	}

	public void setResignDate(LocalDate resignDate) {
		this.resignDate = new SimpleStringProperty(resignDate.toString());
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}

	public boolean isActive() {
		return active.get();
	}

	public void setActive(boolean active) {
		this.active = new SimpleBooleanProperty(active);
	}

	public String getImageName() {
		return imageName.get();
	}

	public void setImageName(String imageName) {
		this.imageName = new SimpleStringProperty(imageName);
	}

	public String getAccountHistory() {
		if (this.accountHistory == null)
			this.accountHistory = new SimpleStringProperty("");
		return accountHistory.get();
	}

	public void setAccountHistory(String accountHistory) {
		this.accountHistory = new SimpleStringProperty(accountHistory);
	}

}
