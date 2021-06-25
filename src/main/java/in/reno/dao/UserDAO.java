package in.reno.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import in.reno.constants.Role;
import in.reno.util.connection_util.Connection;

public class UserDAO {
	private UserDAO() {
		// default consructor
	}

	private static JdbcTemplate jdbcTemplate = new JdbcTemplate(Connection.getConnection());

	/**
	 * This method is used to save the user credentials in the database
	 * 
	 * @param email
	 * @param password
	 * @param role
	 * @param name
	 */
	public static void save(String email, String password, Role role, String name) {
		String sql = "INSERT INTO user_db(email,password,role,name) VALUES(?,?,?,?)";
		Object[] params = { email, password, role.toString(), name };
		jdbcTemplate.update(sql, params);

	}

	/**
	 * This method is used to verify weather the email and password is present or
	 * not
	 * 
	 * @param email
	 * @param password
	 * @param role
	 * @return
	 */
	public static boolean isEmailAndPasswordPresent(String email, String password, Role role) {
		boolean confirmation;
		String sql = "SELECT email FROM user_db WHERE email=? AND password=? AND role=?";
		Object[] param = { email, password, role.toString() };

		try {
			jdbcTemplate.queryForObject(sql, String.class, param);
			confirmation = true;
		} catch (DataAccessException e) {
			confirmation = false;
		}
		return confirmation;

	}

	/**
	 * This methos is used to verify weather the given email is present or not
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmailPresent(String email) {
		boolean confirmation;
		String sql = "SELECT email FROM user_db WHERE email=?";
		try {
			jdbcTemplate.queryForObject(sql, String.class, email);
			confirmation = true;
		} catch (DataAccessException e) {
			confirmation = false;
		}
		return confirmation;
	}

}
