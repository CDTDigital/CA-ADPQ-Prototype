package com.intimetec.crns.util;

/**
 * @author In Time Tec
 */
public class Utils {

	/**
	 * Method to remove the parenthesis.
	 * @param str   the String.
	 * @return      the resultant String.
	 */
	public static String removeParenthesis(final String str) {
		return str.replaceAll("[\\(\\)\\[\\]\\{\\}\"]", "");
	}
}
