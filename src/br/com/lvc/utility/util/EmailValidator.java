package br.com.lvc.utility.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	 
	private Pattern pattern;
	private Matcher matcher;
	
	private static EmailValidator instance = null;
 
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	private EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public static EmailValidator getInstance() {
		if(instance == null)
			instance = new EmailValidator();
		
		return instance;
	}
 

	public boolean isValidEmail(final String email) {
 
		matcher = pattern.matcher(email);
		boolean valid = matcher.matches();
		return valid;
 
	}
}
