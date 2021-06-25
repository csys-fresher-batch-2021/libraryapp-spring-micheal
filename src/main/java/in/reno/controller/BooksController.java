package in.reno.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.reno.exception.LengthException;
import in.reno.model.BookDetails;
import in.reno.model.DebtUser;
import in.reno.service.BooksService;

@RestController
public class BooksController {
	private static final String LOGGED_IN_USER = "LOGGED_IN_USER";

	@Autowired
	BooksService obj;
	
	@GetMapping("getBookByName")
	public List<BookDetails> getBookByName(@RequestParam("bookName") String name) {
		
		return obj.getBookByName(name.toUpperCase());

	}

	@GetMapping("takeBook")
	public String takeBook(@RequestParam("bookName") String bookName, @RequestParam("quantity") int quantity,
			HttpSession sessions) {
		String email = (String) sessions.getAttribute(LOGGED_IN_USER);
		return BooksService.takeBook(bookName.toUpperCase(), quantity, email);

	}

	@GetMapping("returnBook")
	public String returnBook(@RequestParam("bookName") String bookName, @RequestParam("quantity") int quantity,
			HttpSession sessions) {
		String email = (String) sessions.getAttribute(LOGGED_IN_USER);
		return BooksService.returnBook(bookName.toUpperCase(), quantity, email);
	}

	@GetMapping("getDebtUserDetail")
	public List<DebtUser> getDebtUserDetail(HttpSession sessions) {
		String email = (String) sessions.getAttribute(LOGGED_IN_USER);
		return BooksService.getDebtuserDetails(email);
	}

	@GetMapping("uploadBooks")
	public String uploadBooks(@RequestParam("bookName") String bookName, @RequestParam("quantity") int quantity) {
		String confirmation;
		try {
			confirmation = BooksService.uploadBooks(bookName, quantity);
		} catch (LengthException e) {
			confirmation = e.getMessage();
		}
		return confirmation;
	}

}
