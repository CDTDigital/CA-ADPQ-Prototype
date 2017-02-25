/**
 * 
 */
package com.intimetec.crns.core.config.google;

/**
 * @author shiva.dixit
 *
 */
public class GoogleApiConfig {
	private String url;
	private String key;


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public GoogleApiConfig(String googleApiUrl, String googleApiKey) {
		this.url = googleApiUrl;
		this.key = googleApiKey;
	}
}