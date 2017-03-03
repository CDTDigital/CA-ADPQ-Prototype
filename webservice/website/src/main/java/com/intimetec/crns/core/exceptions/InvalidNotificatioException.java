/**
 * 
 */
package com.intimetec.crns.core.exceptions;

/**
 * {@code InvalidNotificatioException} class to handle the invalid 
 * notification exception.
 * @author In Time Tec
 *
 */
@SuppressWarnings("serial")
public class InvalidNotificatioException extends Exception {

	/**
	 * Creating object of the the class {@code InvalidNotificatioException}.
	 */
	public InvalidNotificatioException() {
	}

	/**
	 * @param message the detail message.
	 */
	public InvalidNotificatioException(final String message) {
		super(message);
	}

}
