package com.intimetec.crns.core.service.notification.pushnotification;

import java.util.Collection;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;

/**
 * @author In Time Tec
 *
 */
public interface PushNotificationService 
{
	public void sendNotificationToUsers(Collection<User> users, Notification notification);
}
