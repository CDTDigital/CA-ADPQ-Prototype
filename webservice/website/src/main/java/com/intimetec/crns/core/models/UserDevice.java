package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity model class for the User Device table.
 * @author In Time Tec
 */
@Entity
@Table(name = "user_device")
public class UserDevice {
	/**
	 * Id of the user_device.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;	
	
	/**
	 * User of the device.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	/**
	 * Id of the device.
	 */
	@Column(name = "device_id")
	private String deviceId;
	
	/**
	 * Token of the device.
	 */
	@Column(name = "device_token")
	private String deviceToken;
	
	/**
	 * Type of the device.
	 */
	@Column(name = "device_type")
	private String deviceType;

	/**
	 * Authentication token of the device.
	 */
	@Column(name = "auth_token")
	private String authToken;

	/**
	 * Creating object of class {@code UserDevice}.
	 */
	public UserDevice() {
	}
	

	/**
	 * Creating object of class {@code UserDevice}.
	 * @param user         the User of the device
	 * @param deviceId     the Id of the device
	 * @param deviceType   the type of the device
	 * @param deviceToken  the token of the device
	 * @param authToken    the authentication token of the device.
	 */
	public UserDevice(final User user, final String deviceId, 
			final String deviceType, final String deviceToken, 
			final String authToken) {
		this.user = user;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.deviceToken = deviceToken;
		this.authToken = authToken;
	}
	
	/**
	 * @return id.
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param id the of the user device.
	 */	
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the User of the device.
	 */
	public final User getUser() {
		return user;
	}

	/**
	 * @param user the user of the device.
	 */
	public final void setUser(final User user) {
		this.user = user;
	}

	/**
	 * @return the Id of the device.
	 */
	public final String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the Id of the device.
	 */
	public final void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the token of the device.
	 */
	public final String getDeviceToken() {
		return deviceToken;
	}

	/**
	 * @param deviceToken the token of the device.
	 */
	public final void setDeviceToken(final String deviceToken) {
		this.deviceToken = deviceToken;
	}

	/**
	 * @return the type of the device.
	 */
	public final String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the type of the device.
	 */
	public final void setDeviceType(final String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the authentication token of the device.
	 */
	public final String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken the authentication token of the device.
	 */
	public final void setAuthToken(final String authToken) {
		this.authToken = authToken;
	}
}
