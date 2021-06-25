package in.reno.validator;

import in.reno.exception.LengthException;

/**
 * This method is used to get the size of the string
 * 
 * @author mich2635
 *
 */
public class StringLengthChecker {
	private StringLengthChecker() {
		// default condtructor
	}

	public static void lengthChecker(String name) throws LengthException {
		if (name.length() > 255) {
			throw new LengthException("Character must be less than 255");
		}
	}

}
