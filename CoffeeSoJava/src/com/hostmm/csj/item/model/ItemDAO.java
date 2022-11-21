package com.hostmm.csj.item.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hostmm.csj.database.DBconnection;
import com.hostmm.csj.staff.model.Staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemDAO {

	private Connection connection;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private Statement stmt;

	public int createItem(Item item) {

		connection = DBconnection.getDBconnection();

		int rowEffected = 0;
		try {

			pStmt = connection.prepareStatement(
					"INSERT INTO `csj`.`coffee` (`name`, `price`, `mood`, `description`, `available`, `imageName`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?);");

			pStmt.setString(1, item.getName());
			pStmt.setDouble(2, item.getPrice());
			pStmt.setString(3, item.getMood());
			pStmt.setString(4, item.getDescription());
			pStmt.setString(5, item.getAvailable());
			pStmt.setString(6, item.getImageName());
			pStmt.setString(7, item.getStatus());
			rowEffected = pStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;
	}

	public int updateItem(Item item) {
		int rowEffected = 0;
		connection = DBconnection.getDBconnection();
		try {
			pStmt = connection.prepareStatement(
					"UPDATE `csj`.`coffee` SET `name` = ?, `price` = ?, `mood` = ?, `description` = ?, `available` = ?, `imageName` = ?, `status` = ? WHERE (`id` = ?);");
			pStmt.setString(1, item.getName());
			pStmt.setDouble(2, item.getPrice());
			pStmt.setString(3, item.getMood());
			pStmt.setString(4, item.getDescription());
			pStmt.setString(5, item.getAvailable());
			pStmt.setString(6, item.getImageName());
			pStmt.setString(7, item.getStatus());
			pStmt.setInt(8, item.getId());

			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;

	}

	public ObservableList<Item> getItemList(String sql) {
		ObservableList<Item> itemList = FXCollections.observableArrayList();
		connection = DBconnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				itemList.add(new Item(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("mood"), rs.getString("available"), rs.getString("description"), rs.getString("imageName"), rs.getString("status")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return itemList;

	}

	private void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
