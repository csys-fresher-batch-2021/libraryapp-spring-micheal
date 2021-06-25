package in.reno.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

	@PostMapping("logOut")
	public String logOut(HttpSession sessions) {
		sessions.setAttribute("LOGGED_IN_USER", null);
		return "index.html";

	}

	@PostMapping("isLoggedInCheck")
	public boolean isLoggedInCheck(HttpSession sessions) {

		String verification = (String) sessions.getAttribute("LOGGED_IN_USER");
		boolean confirmation;
		if (verification == null) {
			confirmation = false;
		} else {
			confirmation = true;
		}
		return confirmation;
	}
}
