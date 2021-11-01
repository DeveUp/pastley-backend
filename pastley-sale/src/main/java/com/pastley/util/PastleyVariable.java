package com.pastley.util;

import java.io.Serializable;

public class PastleyVariable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	///////////////////////////////////////////////////////
	// ARRAY
	///////////////////////////////////////////////////////
	public static final char[] PASTLEY_ARRAY_NUMBER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	///////////////////////////////////////////////////////
	// Date
	///////////////////////////////////////////////////////
	public static final String PASTLEY_DATE_ZONA_ID = "America/Bogota";
	public static final String PASTLEY_DATE_FORMAT_DATE = "yyyy/MM/dd";
	public static final String PASTLEY_DATE_FORMAT_HOUR = "HH:mm:ss";
	public static final String PASTLEY_DATE_TIME_FORMAT = PASTLEY_DATE_FORMAT_DATE + " " + PASTLEY_DATE_FORMAT_HOUR;
}
