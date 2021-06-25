package in.reno.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import in.reno.exception.DbException;

@Configuration
public class DataSourceConfig {
	//@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);

	}

	// @Bean
	public static Connection getConnection(DataSource dataSource) throws DbException {
		try {

			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new DbException("Unable to get connection");
		}

	}
}