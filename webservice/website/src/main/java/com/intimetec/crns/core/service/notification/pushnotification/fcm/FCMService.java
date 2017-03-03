package com.intimetec.crns.core.service.notification.pushnotification.fcm;

import java.util.Collection;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.UserDevice;

/**
 * @author In Time Tec
 */
public interface FCMService {
	/**
	 * Method definition for sending notifications.
	 */
	public void sendNotification(Collection<UserDevice> devices, Notification notification);
}
