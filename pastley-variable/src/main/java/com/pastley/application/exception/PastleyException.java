package com.pastley.application.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * @project Pastley-Variable.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Getter
public class PastleyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;

	
	public PastleyException(String message) {
		this(HttpStatus.NOT_FOUND, message);
	}
	
	public PastleyException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}
	
}
