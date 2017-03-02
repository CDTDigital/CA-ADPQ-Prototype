package com.intimetec.crns.core.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @author shiva.dixit
 *
 */
@SuppressWarnings("serial")
public class CurrentUser extends 
org.springframework.security.core.userdetails.User {
	/**
	 * To log the application messages. 
	 * @see Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(
			CurrentUser.class);
	
	/**
	 * Instance of the class {@code User}.
	 */
    private User user;
    /**
	 * Instance of the class {@code DeviceInfo}.
	 */
    private DeviceInfo deviceInfo;
    
    /**
	 * Creating object of the class {@code CurrentUser}.
	 * @param user the current user.
	 */
    public CurrentUser(final User user) {
        super(user.getUserName(), user.getPassword(), AuthorityUtils.
        		createAuthorityList(user.getUserRole().toString()));
        LOGGER.info("User Role: " + user.getUserRole().toString());
        this.user = user;
    }

	/**
	 * @return the user of the application.
	 */
    public final User getUser() {
        return user;
    }

    /**
	 * @return the id of the user.
	 */ 
    public final long getId() {
        return user.getId();
    }

    /**
	 * @return the role of the user.
	 */
    public final UserRole getRole() {
        return user.getUserRole();
    }
	
    /**
	 * @return the device information of the user.
	 */
	public final DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceId     the id of the device.
	 * @param deviceType   the type of the device.
	 * @param deviceToken  the token of the device.
	 */
	public final void setDeviceInfo(final String deviceId, 
			final String deviceType, 
			final String deviceToken) {
		this.deviceInfo = new DeviceInfo(deviceId, deviceType, deviceToken);
	}

	/**
	 * {@code DeviceInfo} class containing the information of the device.
	 */
	public class DeviceInfo {
		/**
		 * Id of the device.
		 */
		private String deviceId;
		/**
		 * Type of the device.
		 */
		private String deviceType;
		/**
		 * Token of the device.
		 */
		private String deviceToken;
		
		/**
		 * Creating object of the class{@code DeviceInfo}.
		 * @param deviceId    the id of the device.
		 * @param deviceType  the type of the device.
		 * @param deviceToken the token of the device.
		 */
		public DeviceInfo(final String deviceId, final String deviceType, 
				final String deviceToken){
			this.deviceId = deviceId;
			this.deviceType = deviceType;
			this.deviceToken = deviceToken;
		}
		
		/**
		 * @return deviceId the id of the device.
		 */
		public String getDeviceId() {
			return deviceId;
		}
		
		/**
		 * @return deviceType the type of the device.
		 */
		public String getDeviceType() {
			return deviceType;
		}
		
		/**
		 * @return deviceToken the token of the device.
		 */
		public String getDeviceToken() {
			return deviceToken;
		}
		
		/**
		 * @return {@String} the string representation of the object.
		 */
		public final String toString() {
			return "deviceId: " + deviceId
					+ ", deviceToken: " + deviceToken
					+ ", deviceType: " + deviceType;
		}
	}
}
