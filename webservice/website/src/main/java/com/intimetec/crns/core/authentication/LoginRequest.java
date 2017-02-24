/**
 * 
 */
package com.intimetec.crns.core.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author shiva.dixit
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
	private String userName;
	private String password;
	private String deviceId;
	private String deviceToken;
	private String deviceType;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	@Override
	public String toString(){
		return "Username: "+userName
				+", password: "+password
				+", deviceId: "+deviceId
				+", deviceToken: "+deviceToken
				+", deviceType: "+deviceType;
	}
}
