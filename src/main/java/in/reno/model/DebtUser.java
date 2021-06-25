package in.reno.model;

import java.time.LocalDate;

public class DebtUser {

	private String email;

	private String bookName;

	private int bookQuantity;

	private LocalDate date;

	private String strDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(int bookQuantity) {
		this.bookQuantity = bookQuantity;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

}
