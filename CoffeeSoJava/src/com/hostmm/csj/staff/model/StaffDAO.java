package com.hostmm.csj.staff.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.hostmm.csj.database.DBconnection;
import com.hostmm.csj.login.model.LoginDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StaffDAO {
	private Connection connection;
	private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;

	public int createStaff(Staff staff) {

		connection = DBconnection.getDBconnection();

		int rowEffected = 0;
		try {

			pStmt = connection.prepareStatement(
					"INSERT INTO `csj`.`staff` ( `userID`, `username`, `password`, `name`, `age`, `gender`, `phone`, `address`, `role`, `salary`, `employDate`, `resignDate`, `status`, `active`, `imageName`, `accountHistory`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

			pStmt.setString(1, staff.getUserID());
			pStmt.setString(2, staff.getUsername());
			pStmt.setString(3, staff.getPassword());
			pStmt.setString(4, staff.getName());
			pStmt.setInt(5, staff.getAge());
			pStmt.setString(6, staff.getGender());
			pStmt.setString(7, staff.getPhone());
			pStmt.setString(8, staff.getAddress());
			pStmt.setString(9, staff.getRole());
			pStmt.setDouble(10, staff.getSalary());
			Date employDate = Date.valueOf(staff.getEmployDate());
			pStmt.setDate(11, employDate);
			Date resignDate = Date.valueOf(staff.getResignDate());
			pStmt.setDate(12, resignDate);
			pStmt.setString(13, staff.getStatus());
			pStmt.setBoolean(14, staff.isActive());
			pStmt.setString(15, staff.getImageName());
			pStmt.setString(16, staff.getAccountHistory());

			rowEffected = pStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;

	}

	public int updateStaff(Staff staff) {

		int rowEffected = 0;
		connection = DBconnection.getDBconnection();
		try {
			pStmt = connection.prepareStatement(
					"UPDATE `csj`.`staff` SET `username` = ?, `password` = ?, `name` = ?, `age` = ?, `gender` = ?, `phone` = ?, `address` = ?, `role` = ?, `salary` = ?, `employDate` = ?, `resignDate` = ?, `status` = ?, `active` = ?, `imageName` = ?, `accountHistory` = ? WHERE (`userID` = ?);");
			pStmt.setString(1, staff.getUsername());
			pStmt.setString(2, staff.getPassword());
			pStmt.setString(3, staff.getName());
			pStmt.setInt(4, staff.getAge());
			pStmt.setString(5, staff.getGender());
			pStmt.setString(6, staff.getPhone());
			pStmt.setString(7, staff.getAddress());
			pStmt.setString(8, staff.getRole());
			pStmt.setDouble(9, staff.getSalary());
			Date employDate = Date.valueOf(staff.getEmployDate());
			pStmt.setDate(10, employDate);
			Date resignDate = Date.valueOf(staff.getResignDate());
			pStmt.setDate(11, resignDate);
			pStmt.setString(12, staff.getStatus());
			pStmt.setBoolean(13, staff.isActive());
			pStmt.setString(14, staff.getImageName());
			pStmt.setString(15, staff.getAccountHistory());
			pStmt.setString(16, staff.getUserID());
			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;

	}

	public ObservableList<Staff> getStaffList(String sql) {
		ObservableList<Staff> staffList = FXCollections.observableArrayList();
		connection = DBconnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				staffList.add(new Staff(rs.getString("userID"), rs.getString("username"), rs.getString("password"),
						rs.getString("name"), rs.getInt("age"), rs.getString("gender"), rs.getString("phone"),
						rs.getString("address"), rs.getString("role"), rs.getDouble("salary"),
						rs.getDate("employDate").toLocalDate(), rs.getDate("resignDate").toLocalDate(),
						rs.getString("status"), rs.getBoolean("active"), rs.getString("imageName"),
						rs.getString("accountHistory")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return staffList;

	}

	public int deleteStaffByID(Staff staff) {
		int rowEffected = 0;
		connection = DBconnection.getDBconnection();
		try {
			pStmt = connection.prepareStatement("UPDATE `staff` SET active = ?,accountHistory = ? where userID = ?; ");
			pStmt.setBoolean(1, false);
			pStmt.setString(2,
					staff.getAccountHistory() + ",deletedBy-" + LoginDAO.getLoggedUserID() + "@" + LocalDate.now());
			pStmt.setString(3, staff.getUserID());
			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowEffected;
	}

	private void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
//