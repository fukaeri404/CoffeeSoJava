package com.hostmm.csj.login.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hostmm.csj.database.DBconnection;
import com.hostmm.csj.utility.crypto.PasswordValidator;

public class LoginDAO {
	private Connection connection;
	private PreparedStatement pStmt;
	private ResultSet rs;
	private static String loggedUserID;
	private static String loggedUsername;

	public boolean isCredentialsValid(String username, String password, String role) throws SQLException {
		connection = DBconnection.getDBconnection();
		boolean signinOk = false;
		String storedPassword = null;

		try {
			pStmt = connection.prepareStatement("select username,password,userID from staff where username=? && role = ?");
			pStmt.setString(1, username);
			pStmt.setString(2, role);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				storedPassword = rs.getString("password");
				loggedUserID = rs.getString("username");
				loggedUserID = rs.getString("userID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (storedPassword != null)
			try {
				signinOk = PasswordValidator.validatePassword(password, storedPassword);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return signinOk;
	}

	public static String getLoggedUserID() {
		return loggedUserID;
	}

	public static String getLoggedUsername() {
		return loggedUsername;
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
