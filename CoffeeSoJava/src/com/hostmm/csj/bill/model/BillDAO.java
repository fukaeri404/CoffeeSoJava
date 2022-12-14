package com.hostmm.csj.bill.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import com.hostmm.csj.database.DBconnection;
import com.hostmm.csj.item.model.Item;
import com.hostmm.csj.item.model.OrderedItem;
import com.hostmm.csj.staff.model.Staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BillDAO {
	private Connection connection;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private Statement stmt;

	static double totalPrice;

	public int createBill(Bill bill) {
		connection = DBconnection.getDBconnection();

		int rowEffected = 0;
		try {

			pStmt = connection.prepareStatement(
					"INSERT INTO `csj`.`history` (`name`, `quantity`, `totalPrice`, `saleMonth`, `saleDate`, `saleYear`, `saleTime`, `cashier`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");

			pStmt.setString(1, bill.getName());
			pStmt.setInt(2, bill.getQuantity());
			pStmt.setString(3, "$ " + bill.getTotalPrice());
			pStmt.setString(4, bill.getSaleMonth());
			pStmt.setString(5, bill.getSaleDate());
			pStmt.setString(6, bill.getSaleYear());
			Time time = Time.valueOf(bill.getSaleTime());
			pStmt.setTime(7, time);
			pStmt.setString(8, bill.getCashier());
			pStmt.setString(9, bill.getStatus());

			rowEffected = pStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;
	}

	public int updateBill(Bill bill) {

		int rowEffected = 0;
		connection = DBconnection.getDBconnection();
		try {
			pStmt = connection.prepareStatement(
					"UPDATE `csj`.`history` SET `name` = ?, `quantity` = ?, `totalPrice` = ?, `saleMonth` = ?, `saleDate` = ?, `saleYear` = ?, `saleTime` = ?, `cashier` = ?, `status` = ?  WHERE (`id` = ?);");
			pStmt.setString(1, bill.getName());
			pStmt.setInt(2, bill.getQuantity());
			pStmt.setString(3, "$ " + bill.getTotalPrice());
			pStmt.setString(4, bill.getSaleMonth());
			pStmt.setString(5, bill.getSaleDate());
			pStmt.setString(6, bill.getSaleYear());
			Time time = Time.valueOf(bill.getSaleTime());
			pStmt.setTime(7, time);
			pStmt.setString(8, bill.getCashier());
			pStmt.setString(9, bill.getStatus());
			pStmt.setInt(10, bill.getId());

			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;
	}
	
	
	public int deleteItemById(Bill bill) {
		int rowEffected = 0;
		connection = DBconnection.getDBconnection();
		try {
			pStmt = connection.prepareStatement("DELETE FROM `csj`.`history` WHERE (`id` = ?);");
			pStmt.setInt(1, bill.getId());
			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowEffected;

	}

	public ObservableList<Bill> getBillList(String sql) {
		totalPrice = 0;
		ObservableList<Bill> billList = FXCollections.observableArrayList();
		connection = DBconnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String[] split = rs.getString("totalPrice").split(" ");
				double tp = Double.parseDouble(split[1]);
				totalPrice += Double.parseDouble(split[1]);
				billList.add(new Bill(rs.getInt("id"),rs.getString("name"), rs.getInt("quantity"), tp, rs.getString("saleMonth"),
						rs.getString("saleDate"), rs.getString("saleYear"), rs.getTime("saleTime").toLocalTime(),
						rs.getString("cashier"), rs.getString("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return billList;

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

	public static double getTotalPrice() {
		return totalPrice;
	}

}
