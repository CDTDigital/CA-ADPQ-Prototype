package com.intimetec.crns.core.config.fcm;

/**
 * {@code FcmConfig} for tracking address of the user.
 * @author shiva.dixit
 *
 */
public class FcmConfig {
	/**
	 * URL of the FCM API Url.
	 */
	private String url;
	
	/**
	 * Key of the FCM API key.
	 */
	private String key;
	
	/**
	 * Key of the FCM API Project Key.
	 */
	private String projectKey;

	/**
	 * Creating object of the {@link FcmConfig}.
	 * @param fcmApiUrl  the URL of the Google FCM API.
	 * @param fcmApiKey  the Key of the Google FCM API.
	 * @param fcmApiProjectKey  the Project Key of the Google FCM API.
	 */
	public FcmConfig(final String fcmApiUrl, final String fcmApiKey, 
			final String fcmApiProjectKey) {
		this.url = fcmApiUrl;
		this.key = fcmApiKey;
		this.projectKey = fcmApiProjectKey;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
}
