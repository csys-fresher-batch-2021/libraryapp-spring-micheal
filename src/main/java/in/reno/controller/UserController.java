package in.reno.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.reno.constants.Role;
import in.reno.exception.InValidEmailException;
import in.reno.exception.InValidPaaswordException;
import in.reno.model.DebtUser;
import in.reno.model.FinedUser;
import in.reno.model.Users;
import in.reno.service.BooksService;
import in.reno.service.UserService;

@RestController
public class UserController {
	
	@PostConstruct
	public void init() {
		BooksService.calculateFine();
	}

	@PostMapping("adminRegisteration")
	public String adminRegisteration(@RequestBody Users user) {
		String confirmation;
		try {
			confirmation = UserService.registration(user.getEmail(), user.getPassword(), Role.ADMIN, user.getName());
		} catch (InValidPaaswordException | InValidEmailException e) {
			confirmation = e.getMessage();
		}
		return confirmation;
	}

	@PostMapping("userRegisteration")
	public String userRegisteration(@RequestBody Users user) {
		String confirmation;
		try {
			confirmation = UserService.userRegistration(user.getEmail(), user.getPassword(), Role.USER, user.getName());
		} catch (InValidPaaswordException | InValidEmailException e) {
			confirmation = e.getMessage();
		}
		return confirmation;
	}

	@PostMapping("adminLogin")
	public String adminLogin(@RequestBody Users user, HttpSession sessions) {
		boolean confirmation = UserService.adminLogin(user.getEmail(), user.getPassword());
		String verify;
		if (confirmation) {
			verify = "Login Successfull";
			sessions.setAttribute("LOGGED_IN_USER", user.getEmail());

		} else {
			verify = "Invalid user credentials";
		}
		return verify;
	}

	@PostMapping("UserLogin")
	public String userLogin(@RequestBody Users user, HttpSession sessions) {

		boolean confirmation = UserService.userLogin(user.getEmail(), user.getPassword());
		String verify;
		if (confirmation) {
			verify = "Login Successfull";
			sessions.setAttribute("LOGGED_IN_USER", user.getEmail());

		} else {
			verify = "Invalid user credentials";
		}
		return verify;
	}

	@GetMapping("getAllRecords")
	public static List<DebtUser> getAllRecords() {
		return UserService.getAllDebtRecords();
	}

	@GetMapping("getFinedUsers")
	public static List<FinedUser> getFinedUser() {
		return UserService.getFinedUsers();
	}

	@GetMapping("payFine")
	public static String payFine(@RequestParam("email") String email) {
		return UserService.payFine(email);
	}
}
