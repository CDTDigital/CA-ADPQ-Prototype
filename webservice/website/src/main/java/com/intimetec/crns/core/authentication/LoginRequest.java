package com.intimetec.crns.core.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author shiva.dixit
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("checkstyle:hiddenfield")
public class LoginRequest {
	/**
	 * User name of the User.
	 */
	private String userName;
	/**
	 * Password of the User.
	 */
	private String password;
	/**
	 * DeviceId of the User.
	 */
	private String deviceId;
	/**
	 * DeviceToken of the User.
	 */
	private String deviceToken;
	/**
	 * DeviceType of the User.
	 */
	private String deviceType;
	
	/**
	 * @return {@String} the user name of the User.
	 */
	public final String getUserName() {
		return userName;
	}
	/**
	 * @param userName the user name of the User.
	 */
	public final void setUserName(final String userName) {
		this.userName = userName;
	}
	/**
	 * @return {@String} the password of the User.
	 */
	public final String getPassword() {
		return password;
	}
	/**
	 * @param password the password of the User.
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}
	/**
	 * @return {@String} the password of the User.
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId of the User.
	 */
	public final void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @return {@String} the password of the User.
	 */
	public final String getDeviceToken() {
		return deviceToken;
	}
	/**
	 * @param deviceToken the deviceToken of the User.
	 */
	public final void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	/**
	 * @return {@String} the password of the User.
	 */
	public final String getDeviceType() {
		return deviceType;
	}
	/**
	 * @param deviceType the device type of the User.
	 */
	public final void setDeviceType(final String deviceType) {
		this.deviceType = deviceType;
	}
	
	@Override
	public final String toString() {
		return "Username: " + userName
				+ ", password: " + password
				+ ", deviceId: " + deviceId
				+ ", deviceToken: " + deviceToken
				+ ", deviceType: " + deviceType;
	}
}
