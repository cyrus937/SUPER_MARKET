package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates
 * @author LET'S DREAM
 *
 */

public class DateUtil {
	
	//The date pattern that is used for conversion. Change as you wish
	private static final String DATE_PATTERN="yyyy-MM-dd";
	
	//The date formatter
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	/**
	 * Returns the given date as a well formatted String
	 */
	public static String format(LocalDate date) {
		if(date==null) {
			return null;
		}else {
			return DATE_FORMATTER.format(date);
		}
	}
	
	/**
	 * Converts a string in the format of the defined {@link DateUtil #DATE_PATTERN}
	 * to a LocalDate object. 
	 */
	public static LocalDate parse(String dateString) {
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}catch (DateTimeParseException e) {
			return null;
		}
	}
	
	/**
	 * Checks the string whether it is a valid date
	 * @param dateString
	 * @return
	 */
	public static boolean validDate(String dateString) {
		return DateUtil.parse(dateString) != null;
	}

}
