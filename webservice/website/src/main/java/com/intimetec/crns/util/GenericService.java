package com.intimetec.crns.util;

public class GenericService {

	public String removeParenthesis(String str)
	{
		return str.replaceAll("[\\(\\)\\[\\]\\{\\}\"]", "");
	}
}
