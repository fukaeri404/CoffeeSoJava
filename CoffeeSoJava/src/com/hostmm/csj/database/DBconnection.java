package com.hostmm.csj.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

	public static Connection getDBconnection() {
		String url = "jdbc:mysql://localhost:3306/csj?useSSL=false";
		String user = "root";
		String password = "1234";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}

}
