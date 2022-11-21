package com.hostmm.csj.bill.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hostmm.csj.database.DBconnection;
import com.hostmm.csj.item.model.OrderedItem;

public class BillDAO {
	private Connection connection;
	private PreparedStatement pStmt;

	public int createBill(Bill bill) {

		connection = DBconnection.getDBconnection();

		int rowEffected = 0;
		try {

			pStmt = connection.prepareStatement(
					"INSERT INTO `csj`.`history` (`name`, `quantity`, `totalPrice`, `saleDate`) VALUES (?, ?, ?, ?);");

			pStmt.setString(1, bill.getName());
			pStmt.setInt(2, bill.getQuantity());
			pStmt.setDouble(3, bill.getTotalPrice());
			Date saleDate = Date.valueOf(bill.getSaleDate());
			pStmt.setDate(4, saleDate);
			rowEffected = pStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;
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
