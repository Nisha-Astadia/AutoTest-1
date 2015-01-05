package com.syngenta.sylk.dbscripts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestOraclezCall {

	public static void main(String[] argv) {

		System.out.println("-------- Oracle JDBC Connection Testing ------");

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;

		}

		System.out.println("Oracle JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager
					.getConnection(
							"jdbc:oracle:thin:@usetlora11.nafta.syngenta.org:1523:SYNAPDV1",
							"SCO_SYLKAPP", "SCO_SYLKAPP");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

}