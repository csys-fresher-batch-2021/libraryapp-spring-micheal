package in.reno.service;

import java.util.List;

import in.reno.constants.Role;
import in.reno.dao.DebtUserDAO;
import in.reno.dao.FinedUserDAO;
import in.reno.dao.UserDAO;
import in.reno.exception.InValidEmailException;
import in.reno.exception.InValidPaaswordException;
import in.reno.model.DebtUser;
import in.reno.model.FinedUser;
import in.reno.validator.UserValidator;

public class UserService {
	private UserService() {
		// default constructor
	}

	/**
	 * This method is used to register the admin
	 * 
	 * @param email
	 * @param password
	 * @param role
	 * @param name
	 * @return
	 * @throws InValidEmailException
	 * @throws InValidPaaswordException
	 */
	public static String registration(String email, String password, Role role, String name)
			throws InValidEmailException, InValidPaaswordException {
		UserValidator.isValidEmail(email);
		UserValidator.isValidPasswordCheck(password);
		boolean isRepeatedUser = UserDAO.isEmailPresent(email);
		String confirmation;
		if (isRepeatedUser) {
			confirmation = "EMAIL ID ALREADY EXIST!!!";
		} else {
			confirmation = "REGISTRATION SUCCESSFULL";
			UserDAO.save(email, password.concat("lib_222"), role, name);
		}
		return confirmation;
	}

	/**
	 * This method is used to register the user
	 * 
	 * @param email
	 * @param password
	 * @param role
	 * @param name
	 * @return
	 * @throws InValidPaaswordException
	 * @throws InValidEmailException
	 */
	public static String userRegistration(String email, String password, Role role, String name)
			throws InValidPaaswordException, InValidEmailException {
		UserValidator.isValidEmail(email);
		UserValidator.isValidPasswordCheck(password);
		boolean isRepeatedUser = UserDAO.isEmailPresent(email);
		String confirmation;
		if (isRepeatedUser) {
			confirmation = "EMAIL ID ALREADY EXIST!!!";
		} else {
			confirmation = "REGISTRATION SUCCESSFULL";
			UserDAO.save(email, password, role, name);
		}
		return confirmation;
	}

	/**
	 * This method is used to login admin
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public static boolean adminLogin(String email, String password) {

		return UserDAO.isEmailAndPasswordPresent(email, password, Role.ADMIN);

	}

	/**
	 * This method is used to login user
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public static boolean userLogin(String email, String password) {

		return UserDAO.isEmailAndPasswordPresent(email, password, Role.USER);
	}

	/**
	 * This method is used to get all the debt user
	 * 
	 * @return
	 */
	public static List<DebtUser> getAllDebtRecords() {
		return DebtUserDAO.findAll();
	}

	/**
	 * This method is used to get all the fined user list
	 * 
	 * @return
	 */
	public static List<FinedUser> getFinedUsers() {
		return FinedUserDAO.findALL();
	}

	/**
	 * This method is used to pay fine
	 * 
	 * @param email
	 * @return
	 */
	public static String payFine(String email) {
		boolean verification = UserValidator.repeatedfinedUserCheck(email);
		String confirmation;
		if (verification) {
			FinedUserDAO.updateToZero(email);
			confirmation = "Paid successfully";

		} else {
			confirmation = "This user didnt have any fine";
		}

		return confirmation;

	}

}
