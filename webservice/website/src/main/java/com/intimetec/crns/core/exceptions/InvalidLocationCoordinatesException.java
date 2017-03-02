package com.intimetec.crns.core.exceptions;

/**
 * {@code InvalidLocationCoordinatesException} class to handle the invalid 
 * location coordinates exception.
 * @author shiva.dixit
 */
@SuppressWarnings("serial")
public class InvalidLocationCoordinatesException extends Exception {

	/**
	 * Creating object of the class{@code InvalidLocationCoordinatesException}.
	 */
	public InvalidLocationCoordinatesException() {
	}

	/**
	 * @param message the detail message.
	 */
	public InvalidLocationCoordinatesException(final String message) {
		super(message);
	}

}
