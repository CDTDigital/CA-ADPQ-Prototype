package com.intimetec.crns.util;

public class Utils {

	public static String removeParenthesis(String str)
	{
		return str.replaceAll("[\\(\\)\\[\\]\\{\\}\"]", "");
	}
}
