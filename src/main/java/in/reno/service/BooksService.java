package in.reno.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import in.reno.dao.BooksDAO;
import in.reno.dao.DebtUserDAO;
import in.reno.dao.FinedUserDAO;
import in.reno.exception.LengthException;
import in.reno.model.BookDetails;
import in.reno.model.DebtUser;
import in.reno.validator.StringLengthChecker;

@Service
public class BooksService {
	

	/**
	 * This method is used to upload books
	 * 
	 * @param bookName
	 * @param quantity
	 * @return
	 * @throws LengthException
	 */
	public static String uploadBooks(String bookName, int quantity) throws LengthException {
		StringLengthChecker.lengthChecker(bookName);
		String confirmation;
		if (BooksDAO.isBookNamePresent(bookName)) {
			BooksDAO.update(bookName, quantity);
			confirmation = "BOOK UPDATED SUCCESSFULLY";
		} else {
			BooksDAO.save(bookName, quantity);
			confirmation = "BOOK UPLOADED SUCCESSFULLY";
		}
		return confirmation;
	}

	/**
	 * This method is used to get all the books
	 * 
	 * @param bookName
	 * @return
	 */
	public  List<BookDetails> getBookByName(String bookName) {
		return BooksDAO.findALLByName(bookName);
	}

	/**
	 * This methods is used to take book ,It automatically updates the resprective
	 * tables
	 * 
	 * @param bookName
	 * @param quatity
	 * @param email
	 * @return
	 */
	public static String takeBook(String bookName, int quatity, String email) {
		String confirmation;
		boolean isFinedUser = FinedUserDAO.isEmailIsPresent(email);
		if (!isFinedUser) {
			boolean verification = BooksDAO.isBookNamePresent(bookName);
			boolean isRepeatedDebtUser = DebtUserDAO.isRepeatedEmail(email, bookName);
			int bookQuantity = BooksDAO.getQuantity(bookName);

			if (verification) {
				if (bookQuantity > 0) {
					if (isRepeatedDebtUser) {
						BooksDAO.update(bookName, quatity * -1);
						DebtUserDAO.update(quatity, bookName, email);
						confirmation = "BOOK TAKEN SUCCESSFULLY";
					} else {
						BooksDAO.update(bookName, quatity * -1);
						LocalDate date = LocalDate.now();
						DebtUserDAO.save(email, bookName, quatity, date);
						confirmation = "BOOK TAKEN SUCCESSFULLY";
					}
				} else {
					confirmation = "SORRY INSUFFICIENT BOOK!!";
				}
			} else {
				confirmation = "SORRY WE DONT HAVE THAT BOOK";
			}
		} else {
			confirmation = "Please Pay the fine First";
		}
		return confirmation;

	}

	/**
	 * This method is used to return the books,It automatically updates the
	 * respective table
	 * 
	 * @param bookName
	 * @param quantity
	 * @param email
	 * @return
	 */
	public static String returnBook(String bookName, int quantity, String email) {
		String confirmation;
		boolean isRepeatedDebtUser = DebtUserDAO.isRepeatedEmail(email, bookName);
		int takenQuantity = DebtUserDAO.getQuantity(bookName, email);
		int remainingQuantity = takenQuantity - quantity;
		if (isRepeatedDebtUser) {
			if (remainingQuantity < 0) {
				confirmation = "YOUR RETURNIG TOO MUCH BOOKS,NOT ACCEPTABLE";
			} else if (remainingQuantity == 0) {
				confirmation = "YOU RETURNED ALL THE BOOKS";
				BooksDAO.update(bookName, quantity);
				DebtUserDAO.delete(email,bookName);
			} else {
				confirmation = "YOU RETURNED SOME BOOKS";
				BooksDAO.update(bookName, quantity);
				DebtUserDAO.update(quantity * -1, bookName, email);
			}

		} else {

			confirmation = "YOU DIDNT TOOK THIS BOOK";
		}
		return confirmation;
	}

	/**
	 * This methos calculates fine at every 12pm
	 */
	@Scheduled(cron = "0 0 1 * * *")
	public static void calculateFine() {
		List<DebtUser> searchResults = DebtUserDAO.findAll();
		LocalDate date = LocalDate.now();
		for (DebtUser obj : searchResults) {
			long noOfDays = ChronoUnit.DAYS.between(obj.getDate(), date);
			if (noOfDays > 3) {
				long fine = (noOfDays - 3) * obj.getBookQuantity() * 5;
				if (FinedUserDAO.isEmailIsPresent(obj.getEmail())) {
					FinedUserDAO.update(obj.getEmail(), fine);
				} else {
					FinedUserDAO.save(obj.getEmail(), fine);

				}
			}
		}
	}

	/**
	 * This methos is used to get all the debt uset list
	 * 
	 * @param email
	 * @return
	 */
	public static List<DebtUser> getDebtuserDetails(String email) {
		return DebtUserDAO.findAllByEmail(email);
	}

}
