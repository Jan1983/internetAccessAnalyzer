package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String formatDateToDbDate(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return df.format(date);
	}

}
