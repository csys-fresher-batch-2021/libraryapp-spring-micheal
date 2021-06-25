package in.reno.util.connection_util;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Connection {
	private Connection() {
		// Default constructor
	}

	private static final String DRIVER_CLASS = System.getenv("spring.datasource.driver-class-name");
	private static final String URL = "jdbc:postgresql://projecttracker.ck1ayq0lncmp.ap-south-1.rds.amazonaws.com/micheal_spring_db";
	private static final String USERNAME = System.getenv("spring.datasource.username");
	private static final String PASSWORD = System.getenv("spring.datasource.password");

	/**
	 * This method establishes connection between core JAVA and database
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static DataSource getConnection() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(DRIVER_CLASS);
		ds.setUrl(URL);
		ds.setUsername(USERNAME);
		ds.setPassword(PASSWORD);
		return ds;

	}

}
