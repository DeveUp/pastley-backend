package com.pastley.util;

public class PastleyVariable {

	public static final char[] PASTLEY_ARRAY_NUMBER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static final String[] PASTLEY_ARRAY_DATE = { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };

	public static final String PASTLEY_PATH_REST = "com.pastley.rest";

	public static final String PASTLEY_DATE_ZONA_ID = "America/Bogota";
	public static final String PASTLEY_DATE_FORMAT_DATE = "yyyy/MM/dd";
	public static final String PASTLEY_DATE_FORMAT_HOUR = "HH:mm:ss";
	public static final String PASTLEY_DATE_TIME_FORMAT = PASTLEY_DATE_FORMAT_DATE + " " + PASTLEY_DATE_FORMAT_HOUR;

	public static final String PASTLEY_CIRCUIT_BREAKER_INSTANCES_A = "mainService";
	public static final String PASTLEY_CIRCUIT_BREAKER_FALLBACK_METHOD = "fallBack";
}
