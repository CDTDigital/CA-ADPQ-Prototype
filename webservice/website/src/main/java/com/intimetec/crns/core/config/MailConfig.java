package com.intimetec.crns.core.config;

/**
 * {@code MailConfig} for tracking address of the user.
 * @author shiva.dixit
 *
 */
public class MailConfig {
	/**
	 * Host Name of mail server.
	 */
	private String host;
	/**
	 * Port of mail server for TLS/SSL.
	 */
	private String port;
	/**
	 * Email used for sending mails for notifications
	 */
	private String userName;
	/**
	 * Password of email account being used to send notifications
	 */
	private String password;

	/**
	 * Creating object of the {@link MailConfig}.
	 * @param googleApiUrl  the URL of the Google API.
	 * @param googleApiKey  the key of the Google API.
	 */
	public MailConfig(final String host, 
			final String port, String userName, String password) {
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * @return {@String} the Host name of the Mail server.
	 */
	public final String getHost() {
		return host;
	}

	/**
	 * @return {@String} the port of the Mail server.
	 */
	public final String getPort() {
		return port;
	}
	
	/**
	 * @return {@String} the Host name of the Mail server.
	 */
	public final String getUserName() {
		return userName;
	}

	/**
	 * @return {@String} the port of the Mail server.
	 */
	public final String getPassword() {
		return password;
	}
}
