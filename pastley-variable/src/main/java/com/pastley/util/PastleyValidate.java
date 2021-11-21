package com.pastley.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @project Pastley-Variable.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class PastleyValidate implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Pattern pattern = Pattern.compile(PastleyVariable.PASTLEY_VALIDATE_PATTERN_EMAIL);

	/**
	 * Method that allows to validate the strings.
	 * 
	 * @param chain, Represents the string.
	 * @return Boolean true if it meets false if not.
	 */
	public static boolean isChain(String chain) {
		return chain != null && chain.trim().length() > 0;
	}

	/**
	 * Method that allows validating if a string is an email.
	 * @param chain, Represents the string.
	 * @return Boolean true if it meets false if not.
	 */
	public static boolean isEmail(String chain) {
		if (isChain(chain)) {
			Matcher mather = pattern.matcher(chain);
			return mather.find();
		}
		return false;
	}

	/**
	 * Method that validates if a string contains pure numbers.
	 * 
	 * @param str, Represents the string.
	 * @return Boolean true if it meets false if not.
	 */
	public static boolean isNumber(String str) {
		if (isChain(str)) {
			char[] array = PastleyVariable.PASTLEY_ARRAY_NUMBER;
			char[] aux = str.toCharArray();
			for (char i : aux) {
				boolean salir = true;
				for (char j : array) {
					if (i == j)
						salir = false;
				}
				if (salir)
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Method that allows you to convert a string to uppercase.
	 * 
	 * @param chain, Represents the string.
	 * @return The converted string.
	 */
	public static String uppercase(String chain) {
		return isChain(chain) ? chain.toUpperCase() : chain;
	}

	/**
	 * Method that allows verifying if a biginteger is greater than zero.
	 * 
	 * @param a, Represents the biginteger.
	 * @return true if it meets false if not.
	 */
	public static boolean bigIntegerHigherZero(BigInteger a) {
		return (a != null && a.compareTo(BigInteger.ZERO) == 1);
	}

	/**
	 * Method that allows verifying if a biginteger is less than zero.
	 * 
	 * @param a, Represents the biginteger.
	 * @return true if it meets false if not.
	 */
	public static boolean bigIntegerLessZero(BigInteger a) {
		return (a != null && a.compareTo(BigInteger.ZERO) == -1);
	}

	/**
	 * Method that allows verifying if a biginteger is greater than or equal to
	 * zero.
	 * 
	 * @param a, Represents the biginteger.
	 * @return true if it meets false if not.
	 */
	public static boolean bigIntegerHigherEqualZero(BigInteger a) {
		return (a != null && a.compareTo(BigInteger.ZERO) >= 0);
	}
}
