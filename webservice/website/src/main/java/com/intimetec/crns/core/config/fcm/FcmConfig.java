package com.intimetec.crns.core.config.fcm;

/**
 * {@code FcmConfig} for tracking address of the user.
 * @author shiva.dixit
 *
 */
public class FcmConfig {
	/**
	 * URL of the Google API.
	 */
	private String url;

	/**
	 * Creating object of the {@link FcmConfig}.
	 * @param fcmApiUrl  the URL of the Google FCM API.
	 */
	public FcmConfig(final String fcmApiUrl) {
		this.url = fcmApiUrl;
	}
	
	/**
	 * @return {@String} the URL of the Google API.
	 */
	public final String getUrl() {
		return url;
	}
	
	/**
	 * @param url the URL of the Google API.
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}
}
