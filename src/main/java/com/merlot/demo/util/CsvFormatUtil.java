package com.merlot.demo.util;

public class CsvFormatUtil {

	public static String formatField(String s) {
		return "\"" + s + "\"";
	}
	
	public static String parseField(String s) {
		if(s.startsWith("\"")) {
			s = s.substring(1);
		}
		if(s.endsWith("\"")) {
			s = s.substring(0, s.length() -1);
		}
		return s;
	}
}
