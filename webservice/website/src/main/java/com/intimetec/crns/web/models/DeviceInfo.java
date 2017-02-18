package com.intimetec.crns.web.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for User table
 * @author shiva.dixit
 */
@Entity
@Table(name="device_info")
public class DeviceInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="devinfo_id")
	private int id;

	@Column(name="device_id")
	private String deviceId;
	
	@Column(name="device_token")
	private String deviceToken;
	
	@Column(name="device_type")
	private String deviceType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
