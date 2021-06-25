package in.reno.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.reno.dao.FinedUserDAO;
import in.reno.exception.InValidEmailException;
import in.reno.exception.InValidPaaswordException;

public class UserValidator {
	private UserValidator() {
		// default constructor
	}

	/**
	 * This method checks the given email is present or not
	 * 
	 * @param email
	 * @return
	 */
	public static boolean repeatedfinedUserCheck(String email) {
		return FinedUserDAO.isEmailIsPresent(email);
	}

	/**
	 * This method checks weather the given password is strengthfull or not
	 * 
	 * @param password
	 * @throws InValidPaaswordException
	 */
	public static void isValidPasswordCheck(String password) throws InValidPaaswordException {
		if (password == null || password.trim().equals("") || password.length() < 4 || password.length() > 255) {
			throw new InValidPaaswordException("In valid Password");
		}
	}

	/**
	 * This method checks weather the given email is valid or not
	 * 
	 * @param email
	 * @throws InValidEmailException
	 */
	public static void isValidEmail(String email) throws InValidEmailException {
		String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		boolean verification = matcher.matches();
		if (!verification) {
			throw new InValidEmailException("Invalid email");
		}
	}
}
