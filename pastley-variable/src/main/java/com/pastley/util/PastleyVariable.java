package com.pastley.util;

/**
 * @project Pastley-Variable.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
public class PastleyVariable {

	/* Array */
	public static final char[] PASTLEY_ARRAY_NUMBER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static final String[] PASTLEY_ARRAY_DATE = { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };

	/* Validate */
	public static final String PASTLEY_VALIDATE_PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/* Swagger */
	public static final String PASTLEY_PATH_REST = "com.pastley.rest";
	
	/* Users */
	public static final Long PASTLEY_USER_ADMINISTRATOR_ID = 1L;
	public static final Long PASTLEY_USER_CASHIER_ID = 2L;
	public static final Long PASTLEY_USER_CUSTOMER_ID = 3L;

	/* Date */
	public static final String PASTLEY_DATE_ZONA_ID = "America/Bogota";
	public static final String PASTLEY_DATE_FORMAT_DATE = "yyyy/MM/dd";
	public static final String PASTLEY_DATE_FORMAT_HOUR = "HH:mm:ss";
	public static final String PASTLEY_DATE_TIME_FORMAT = PASTLEY_DATE_FORMAT_DATE + " " + PASTLEY_DATE_FORMAT_HOUR;

	/* Circuit Breaker */
	public static final String PASTLEY_CIRCUIT_BREAKER_INSTANCES_A = "mainService";
	public static final String PASTLEY_CIRCUIT_BREAKER_FALLBACK_METHOD = "fallBack";
}
