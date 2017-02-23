package com.intimetec.crns.core.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUser.class);

    private User user;
    private DeviceInfo deviceInfo;

    public CurrentUser(User user) {
        super(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserRole().toString()));
        LOGGER.info("User Role: "+user.getUserRole().toString());
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return user.getId();
    }

    public UserRole getRole() {
        return user.getUserRole();
    }
	
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceId, String deviceType, String deviceToken) {
		this.deviceInfo = new DeviceInfo(deviceId, deviceType, deviceToken);
	}

	public class DeviceInfo {
		private String deviceId;
		private String deviceType;
		private String deviceToken;
		
		public DeviceInfo(String deviceId, String deviceType, String deviceToken){
			this.deviceId = deviceId;
			this.deviceType = deviceType;
			this.deviceToken = deviceToken;
		}
		
		public String getDeviceId() {
			return deviceId;
		}

		public String getDeviceType() {
			return deviceType;
		}

		public String getDeviceToken() {
			return deviceToken;
		}
		
		public String toString(){
			return "deviceId: "+deviceId
					+", deviceToken: "+deviceToken
					+", deviceType: "+deviceType;
		}
	}
}
