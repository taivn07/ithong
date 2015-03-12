package com.example.ithonge.utils;


public class Utils {

	public static String ReNameFilter(String Name) {
		String FName = Name;
		int i = FName.length();
		if (FName.charAt(i - 1) == ')') {
			while (FName.charAt(i - 1) != '(') {
				i--;
			}
			FName = FName.substring(0, i - 1);
		}
		return FName;
	}
	
	public static String ltrim(String source)

	{

	  return source.replaceAll("^\\s+", "");

	}
	
}
