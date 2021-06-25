package in.reno.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import in.reno.model.DebtUser;
import in.reno.util.connection_util.Connection;

public class DebtUserDAO {
	private DebtUserDAO() {
		// default constructor
	}

	private static JdbcTemplate jdbcTemplate = new JdbcTemplate(Connection.getConnection());

	/**
	 * This method is used to save the debt user details in the database
	 * 
	 * @param email
	 * @param book
	 * @param quantity
	 * @param date
	 */
	public static void save(String email, String book, int quantity, LocalDate date) {

		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		String sql = "INSERT INTO debtuser_db(email,book,date,quantity) VALUES (?,?,?,?)";
		Object[] params = { email, book, sqlDate, quantity };
		jdbcTemplate.update(sql, params);
	}

	/**
	 * This methos is used to find weather the user is present or not
	 * 
	 * @param email
	 * @param bookName
	 * @return
	 */
	public static boolean isRepeatedEmail(String email, String bookName) {
		boolean confirmation;
		String sql = "SELECT email FROM debtuser_db WHERE email=? AND book=?";
		Object[] param = { email, bookName };
		try {
			jdbcTemplate.queryForObject(sql, String.class, param);
			confirmation = true;
		} catch (DataAccessException e) {
			confirmation = false;
		}
		return confirmation;
	}

	/**
	 * This method is used to update the debt user database
	 * 
	 * @param quantity
	 * @param bookName
	 * @param email
	 */
	public static void update(int quantity, String bookName, String email) {
		String sql = "UPDATE debtuser_db SET quantity=quantity+? WHERE book=? AND email=?";
		Object[] params = { quantity, bookName, email };
		jdbcTemplate.update(sql, params);
	}

	/**
	 * This method is used to get the quantity of the given book and email
	 * 
	 * @param bookName
	 * @param email
	 * @return
	 */
	public static Integer getQuantity(String bookName, String email) {
		String sql = "SELECT quantity FROM debtuser_db WHERE book=? AND email=?";
		int quantity;
		try {
			quantity = jdbcTemplate.queryForObject(sql, Integer.class, bookName, email);
		} catch (DataAccessException e) {
			quantity = 0;
		}
		return quantity;

	}

	/**
	 * This method is used to delete the record from the debt user database
	 * 
	 * @param emailId
	 */
	public static void delete(String emailId,String book) {
		String sql = "DELETE FROM debtuser_db WHERE email=? AND book=?";

		jdbcTemplate.update(sql, emailId,book);

	}

	/**
	 * This method id used to get all the records from the debt user data base
	 * 
	 * @return
	 */
	public static List<DebtUser> findAll() {
		String sql = "SELECT * FROM debtuser_db";
		return jdbcTemplate.query(sql, (rs, row) -> {
			String email1 = rs.getString("email");
			Date dt = rs.getDate("date");
			String book = rs.getString("book");
			Integer quantity = rs.getInt("quantity");
			String strDate = dt.toString();
			LocalDate date = LocalDate.parse(strDate);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String dateStr = formatter.format(date);
			DebtUser obj = new DebtUser();
			obj.setDate(date);
			obj.setEmail(email1);
			obj.setStrDate(dateStr);
			obj.setBookName(book);
			obj.setBookQuantity(quantity);

			return obj;

		});

	}

	/**
	 * This method is used to get all records related to the given email
	 * 
	 * @param email
	 * @return
	 */
	public static List<DebtUser> findAllByEmail(String email) {
		String sql = "SELECT * FROM debtuser_db WHERE email=?";
		return jdbcTemplate.query(sql, (rs, row) -> {
			String email1 = rs.getString("email");
			Date dt = rs.getDate("date");
			String book = rs.getString("book");
			Integer quantity = rs.getInt("quantity");
			String strDate = dt.toString();
			LocalDate date1 = LocalDate.parse(strDate);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String date = formatter.format(date1);
			DebtUser obj = new DebtUser();
			obj.setEmail(email1);
			obj.setStrDate(date);
			obj.setBookName(book);
			obj.setBookQuantity(quantity);

			return obj;

		}, email);
	}

}
