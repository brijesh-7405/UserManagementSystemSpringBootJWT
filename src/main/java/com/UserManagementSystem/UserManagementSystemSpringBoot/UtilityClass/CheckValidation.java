package com.UserManagementSystem.UserManagementSystemSpringBoot.UtilityClass;

import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.User;

public class CheckValidation {
	static final Logger LOG = LogManager.getLogger(CheckValidation.class.getName());
	private transient static String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

	public static boolean validatepwd(String pwd) {
		if (Pattern.matches(regex, pwd)) {
			LOG.debug("Password Validation Pass");
			return false;
		} else {
			LOG.debug("Password Validation Fails");
			return true;
		}
	}

	public static String validData(User user, String repwd) {
		String message = "";
		if (user.getEmail().equals("")) {
			message = "*Email is required";
		} else if (user.getPassword().equals("")) {
			message = "*Password is required";
		} else if (String.valueOf(user.getPhone()).length() < 10) {
			message = "*Number not less than 10 Digits";
		} else if (validatepwd(user.getPassword())) {
			message = "*Please Choose Strong Password.";
		} else if (!user.getPassword().equals(repwd)) {
			message = "*Confirm password Should be same as Password.";
		} else {
			message = "valid";
		}
		return message;
	}
}
