package com.pastley.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @project Pastley-Sale.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors soleimygomez, leynerjoseoa, jhonatanbeltran.
 * @version 1.0.0.
 */
public class PastleyDate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String date;
	
	private LocalDate localDate;
	private LocalTime localTime;
	private LocalDateTime localDateTime;
	
	private ZoneId zone;
	private DateTimeFormatter format;
	
	///////////////////////////////////////////////////////
	// Builders
	///////////////////////////////////////////////////////
	public PastleyDate() {
		initZoneId(null);
		initFormat(null);
	}
	
	///////////////////////////////////////////////////////
	// Init
	///////////////////////////////////////////////////////
	/**
	 * Method that initializes the zone.
	 * 
	 * @param zone - Represents the time zone.
	 */
	public void initZoneId(String zone) {
		this.zone = ZoneId.of((PastleyValidate.isChain(zone)) ? zone : PastleyVariable.PASTLEY_DATE_ZONA_ID);
	}

	/**
	 * Method that allows you to format the dates.
	 * 
	 * @param format - Represents the format to use.
	 */
	public void initFormat(String format) {
		this.format = DateTimeFormatter.ofPattern((PastleyValidate.isChain(format)) ? format : PastleyVariable.PASTLEY_DATE_TIME_FORMAT);
	}
	
	///////////////////////////////////////////////////////
	// Method - Current
	///////////////////////////////////////////////////////
	/**
	 * Method that allows obtaining the current date.
	 * 
	 * @param format, Represents the format to use.
	 * @return The string obtained.
	 */
	public String currentToDate(String format) {
		initFormat((PastleyValidate.isChain(format)) ? format : PastleyVariable.PASTLEY_DATE_FORMAT_DATE);
		localDate = LocalDate.now();
		this.date = localDate.format(this.format);
		return date;
	}
	
	/**
	 * Method that allows obtaining the current time.
	 * 
	 * @param format, Represents the format to use.
	 * @return The string obtained.
	 */
	public String currentTime(String format) {
		initFormat((PastleyValidate.isChain(format)) ? format : PastleyVariable.PASTLEY_DATE_FORMAT_HOUR);
		localTime = LocalTime.now();
		this.date = localTime.format(this.format);
		return date;
	}
	
	/**
	 * Method that allows obtaining the current date and time.
	 * 
	 * @param format, Represents the format to use.
	 * @return The string obtained.
	 */
	public String currentToDateTime(String format) {
		initFormat((PastleyValidate.isChain((format)) ? format : PastleyVariable.PASTLEY_DATE_TIME_FORMAT));
		this.localDateTime = LocalDateTime.now();
		this.date = localDateTime.format(this.format);
		return date;
	}
	
	///////////////////////////////////////////////////////
	// Method - Convert
	///////////////////////////////////////////////////////
	/**
	 * Method that converts a date.
	 * @param date, Represents the convert date.
	 * @return the converted date.
	 * @throws ParseException The error presented.
	 */
	public Date convertToDate(String date) throws ParseException {
		 SimpleDateFormat dt = new SimpleDateFormat(PastleyVariable.PASTLEY_DATE_FORMAT_DATE);
	     return dt.parse(date);
	}
	
	/**
	 * Method that converts a date time.
	 * @param date, Represents the convert date.
	 * @return the converted date.
	 * @throws ParseException The error presented.
	 */
	public Date convertToDateTime(String date) throws ParseException {
		 SimpleDateFormat dt = new SimpleDateFormat(PastleyVariable.PASTLEY_DATE_TIME_FORMAT);
	     return dt.parse(date);
	}
	
	///////////////////////////////////////////////////////
	// Method - Format
	///////////////////////////////////////////////////////
	public String formatToDateTime(Date date, String format) {
		if(date != null) {
			if(!PastleyValidate.isChain(format)) {
				format = PastleyVariable.PASTLEY_DATE_TIME_FORMAT;
			}
			DateFormat f = new SimpleDateFormat(format);
			return f.format(date);
		}
		return null;
	}
	
	///////////////////////////////////////////////////////
	// Method - Combine
	///////////////////////////////////////////////////////
	public Date combineFormatToDateTime(Date date, String format) {
		try {
			return convertToDateTime(formatToDateTime(date, format));
		} catch (ParseException e) {
			return null;
		}
	}
	
	///////////////////////////////////////////////////////
	// Getter and Setter
	///////////////////////////////////////////////////////
	public ZoneId getZone() {
		return zone;
	}

	public LocalTime getLocalTime() {
		return localTime;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public void setZone(ZoneId zone) {
		this.zone = zone;
	}

	public DateTimeFormatter getFormat() {
		return format;
	}

	public void setFormat(DateTimeFormatter format) {
		this.format = format;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
