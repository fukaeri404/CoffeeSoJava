package com.hostmm.csj.utility.features;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hostmm.csj.database.DBconnection;

public class CommonDAO {
	private Statement stmt;
	private ResultSet rs;
	private Connection connection;

	public int getRowCount(String tableName) {
		connection = DBconnection.getDBconnection();
		int rowNumbers = 0;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select count(*) from staff;");
			while(rs.next()) {
				rowNumbers = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowNumbers;
	}

	private void close() {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
