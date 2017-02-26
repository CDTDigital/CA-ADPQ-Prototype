/**
 * 
 */
package com.intimetec.crns.util;

import java.util.ArrayList;
import java.util.Collection;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserLocation;
import com.intimetec.crns.core.models.UserNotification;
import com.intimetec.crns.core.restmodels.RestLocation;
import com.intimetec.crns.core.restmodels.RestNotification;
import com.intimetec.crns.core.restmodels.RestUser;
import com.intimetec.crns.core.restmodels.RestUserNotification;

/**
 * @author shiva.dixit
 *
 */
public class RestObjectToModelObjectMapper {

	public static User RestUserToUser(RestUser restUser) {
		User user = new User();
		if (user != null) {
			user.setId(restUser.getId());
			user.setEmail(restUser.getEmail());
			user.setFirstName(restUser.getFirstName());
			user.setLastName(restUser.getLastName());
			user.setMobileNo(restUser.getMobileNo());
			user.setUserName(restUser.getUserName());
			user.setPassword(restUser.getPassword());
			user.setUserRole(restUser.getUserRole());
			user.setUserNotificationOptions(restUser.getUserNotificationOptions());
		}
		return user;
	}

	public static RestUser UserToRestUser(User user) {
		RestUser restUser = new RestUser();
		if (user != null) {
			restUser.setId(user.getId());
			restUser.setEmail(user.getEmail());
			restUser.setFirstName(user.getFirstName());
			restUser.setLastName(user.getLastName());
			restUser.setMobileNo(user.getMobileNo());
			restUser.setUserName(user.getUserName());
			restUser.setPassword(user.getPassword());
			restUser.setUserRole(user.getUserRole());
			restUser.setEnabled(user.isEnabled());
			restUser.setAccountSetupDone();
			restUser.setUserNotificationOptions(user.getUserNotificationOptions());
		}
		return restUser;
	}

	public static Collection<RestUser> UserToRestUser(Collection<User> users) {
		Collection<RestUser> restUsers = new ArrayList<RestUser>();
		for (User user : users) {
			restUsers.add(UserToRestUser(user));
		}
		return restUsers;
	}

	public static UserLocation RestLocationToUserLocation(RestLocation restLocation) {
		UserLocation userLocation = new UserLocation();
		if (restLocation != null) {
			userLocation.setId(restLocation.getId());
			userLocation.setAddressLine1(restLocation.getAddressLine1());
			userLocation.setAddressLine2(restLocation.getAddressLine2());
			userLocation.setCity(restLocation.getCity());
			userLocation.setZipCode(restLocation.getZipCode());
			userLocation.setPlaceId(restLocation.getPlaceId());
			userLocation.setLatitude(restLocation.getLatitude());
			userLocation.setLongitude(restLocation.getLongitude());
			userLocation.setCurrentLocation(restLocation.isCurrentLocation());
		}
		return userLocation;
	}

	public static RestLocation UserLocationToRestLocation(UserLocation userLocation) {
		RestLocation restLocation = new RestLocation();
		if (userLocation != null) {
			restLocation.setId(userLocation.getId());
			restLocation.setAddressLine1(userLocation.getAddressLine1());
			restLocation.setAddressLine2(userLocation.getAddressLine2());
			restLocation.setCity(userLocation.getCity());
			restLocation.setZipCode(userLocation.getZipCode());
			restLocation.setPlaceId(userLocation.getPlaceId());
			restLocation.setLatitude(userLocation.getLatitude());
			restLocation.setLongitude(userLocation.getLongitude());
			restLocation.setCurrentLocation(userLocation.isCurrentLocation());
		}
		return restLocation;
	}

	public static RestNotification NotificationToRestNotification(Notification notification) {
		RestNotification restNotification = new RestNotification();
		if (notification != null) {
			restNotification.setId(notification.getId());
			restNotification.setAddress(notification.getAddress());
			restNotification.setCity(notification.getCity());
			restNotification.setZipCode(notification.getZipCode());
			restNotification.setSubject(notification.getSubject());
			restNotification.setMessage(notification.getMessage());
			if(notification.getSentBy()!=null) {
				restNotification.setUserInfo(notification.getSentBy().getFirstName(), notification.getSentBy().getLastName(), notification.getSentBy().getEmail());
			}
			restNotification.setSentTime(notification.getSentTime());
			restNotification.setValidThrough(notification.getValidThrough());
		}
		return restNotification;
	}
	
	public static RestUserNotification UserNotificationToRestUserNotification(UserNotification userNotification) {
		RestUserNotification restUserNotification = new RestUserNotification();
		if (userNotification != null) {
			restUserNotification.setId(userNotification.getId());
			restUserNotification.setUserId(userNotification.getUserId());
			restUserNotification.setNotification(NotificationToRestNotification(userNotification.getNotification()));
			restUserNotification.setRead(userNotification.isRead());
		}
		return restUserNotification;
	}
}
