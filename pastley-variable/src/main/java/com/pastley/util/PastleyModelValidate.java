package com.pastley.util;

import java.math.BigInteger;

import com.pastley.util.exception.PastleyException;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PastleyModelValidate {

	public void isString(String name, String message) {
		if (!PastleyValidate.isChain(name))
			throw new PastleyException(message);
	}
	
	public void isLong(Long value, String message) {
		if(!PastleyValidate.isLong(value))
			throw new PastleyException(message);
	}

	public void isNumber(String name, String message) {
		if (!PastleyValidate.isNumber(name))
			throw new PastleyException(message);
	}

	public void isNumberHigher(int number, int value, String message) {
		if (number < value)
			throw new PastleyException(message);
	}

	public void isNumberHigher(BigInteger number, BigInteger value, String message) {
		if (number != null && value != null && number.compareTo(value) == 1)
			return;
		throw new PastleyException(message);
	}

	public void isNumberLess(int number, int value, String message) {
		if (number > value)
			throw new PastleyException(message);
	}
}
