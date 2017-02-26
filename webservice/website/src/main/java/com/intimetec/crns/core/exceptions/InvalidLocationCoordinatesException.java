package com.intimetec.crns.core.exceptions;

/**
 * @author shiva.dixit
 *
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
