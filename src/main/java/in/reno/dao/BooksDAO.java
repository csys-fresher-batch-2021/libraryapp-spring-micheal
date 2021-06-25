package in.reno.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import in.reno.model.BookDetails;
import in.reno.util.connection_util.Connection;

public class BooksDAO {
	private BooksDAO() {
		// default constructor
	}

	private static JdbcTemplate jdbcTemplate = new JdbcTemplate(Connection.getConnection());

	/**
	 * This method saves book details in the data base
	 * 
	 * @param name
	 * @param quantity
	 */
	public static void save(String name, int quantity) {

		String sql = "INSERT INTO books_db(name,quantity) VALUES (?,?)";
		Object[] params = { name, quantity };
		jdbcTemplate.update(sql, params);
	}

	/**
	 * This method updates the book details in the data base
	 * 
	 * @param bookName
	 * @param quantity
	 */
	public static void update(String bookName, int quantity) {
		String sql = "UPDATE books_db SET quantity=quantity+? WHERE name=?";
		Object[] params = { quantity, bookName };
		jdbcTemplate.update(sql, params);
	}

	/**
	 * This method is used to get all the books from the data base
	 * 
	 * @return
	 */
	public static List<BookDetails> findAll() {
		String sql = "SELECT * FROM books_db";

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			String name = rs.getString("name");
			int quantity = rs.getInt("quantity");
			BookDetails obj = new BookDetails();
			obj.setName(name);
			obj.setQuantity(quantity);
			return obj;
		});

	}

	/**
	 * This method is used to verify weather the book is present or not
	 * 
	 * @param bookName
	 * @return
	 */
	public static boolean isBookNamePresent(String bookName) {
		boolean confirmation;
		String sql = "SELECT name FROM books_db WHERE name=?";

		try {
			jdbcTemplate.queryForObject(sql, String.class, bookName);
			confirmation = true;
		} catch (DataAccessException e) {
			confirmation = false;
		}
		return confirmation;

	}

	/**
	 * This method is used to get get all the books related to the given name
	 * 
	 * @param bookName
	 * @return
	 */
	public static List<BookDetails> findALLByName(String bookName) {
		String sql = "SELECT * FROM books_db WHERE name LIKE ?";
		Object[] param = { "%" + bookName + "%" };
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			String name = rs.getString("name");
			int quantity = rs.getInt("quantity");
			BookDetails obj = new BookDetails();
			obj.setName(name);
			obj.setQuantity(quantity);
			return obj;
		}, param);
	}

	/**
	 * This method is used to get the quantity of the given book
	 * 
	 * @param bookName
	 * @return
	 */
	public static Integer getQuantity(String bookName) {
		int quantity;
		String sql = "SELECT quantity FROM books_db WHERE name=?";
		Object[] param = { bookName };

		try {
			quantity = jdbcTemplate.queryForObject(sql, Integer.class, param);
		} catch (DataAccessException e) {
			quantity = 0;
		}
		return quantity;

	}

}
