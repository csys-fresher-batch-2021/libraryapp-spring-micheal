package in.reno.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import in.reno.model.FinedUser;
import in.reno.util.connection_util.Connection;

public class FinedUserDAO {

	private FinedUserDAO() {
		// default constructor
	}

	private static JdbcTemplate jdbcTemplate = new JdbcTemplate(Connection.getConnection());

	/**
	 * This method is used to save the fined user to the fined used database
	 * 
	 * @param email
	 * @param fineAmount
	 */
	public static void save(String email, Long fineAmount) {
		String sql = "INSERT INTO fined_user_db VALUES(?,?)";
		Integer amount = Integer.parseInt(fineAmount.toString());
		jdbcTemplate.update(sql, email, amount);

	}

	/**
	 * This is method is used to updata the fined user database
	 * 
	 * @param email
	 * @param fineAmount
	 */
	public static void update(String email, Long fineAmount) {
		String sql = "UPDATE  fined_user_db SET amout=? WHERE email=?";
		Integer amount = Integer.parseInt(fineAmount.toString());
		jdbcTemplate.update(sql, amount, email);
	}

	/**
	 * This method is used to condfirm wheather the email is present or not
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmailIsPresent(String email) {
		boolean confirmation;
		String sql = "SELECT email FROM fined_user_db WHERE email=?";

		try {
			jdbcTemplate.queryForObject(sql, String.class, email);
			confirmation = true;
		} catch (DataAccessException e) {
			confirmation = false;
		}
		return confirmation;
	}

	/**
	 * This method is used to get all the user from the fined user database
	 * 
	 * @return
	 */
	public static List<FinedUser> findALL() {
		String sql = "SELECT * FROM fined_user_db";
		return jdbcTemplate.query(sql, (rs, row) -> {
			String email = rs.getString("email");
			Integer amount = rs.getInt("amout");

			FinedUser obj = new FinedUser();
			obj.setEmail(email);
			obj.setAmount(amount);

			return obj;

		});
	}

	/**
	 * This method is used to update the fine amount to 0 of the respective email id
	 * 
	 * @param email
	 */
	public static void updateToZero(String email) {
		String sql = "DELETE FROM fined_user_db WHERE email=?";
		jdbcTemplate.update(sql, email);

	}

}
