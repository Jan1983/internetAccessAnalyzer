package com.analytic;

import java.text.DecimalFormat;

public class IOUtils {

	
	public static String round(double number) {
		return round(number, "#.###");
	}
	
	public static String round(double number, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(number);
	}
}
