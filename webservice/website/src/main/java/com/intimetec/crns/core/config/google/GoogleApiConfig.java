package com.intimetec.crns.core.config.google;

/**
 * {@code GoogleApiConfig} for tracking address of the user.
 * @author shiva.dixit
 *
 */
public class GoogleApiConfig {
	/**
	 * URL of the Google API.
	 */
	private String url;
	/**
	 * Key of the Google API.
	 */
	private String key;

	/**
	 * Creating object of the {@link GoogleApiConfig}.
	 * @param googleApiUrl  the URL of the Google API.
	 * @param googleApiKey  the key of the Google API.
	 */
	public GoogleApiConfig(final String googleApiUrl, 
			final String googleApiKey) {
		this.url = googleApiUrl;
		this.key = googleApiKey;
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

	/**
	 * @return {@String} the Key of the Google API.
	 */
	public final String getKey() {
		return key;
	}

	/**
	 * @param key the Key of the Google API.
	 */
	public final void setKey(final String key) {
		this.key = key;
	}
}
