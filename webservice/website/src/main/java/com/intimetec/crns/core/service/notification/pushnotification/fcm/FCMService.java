package com.intimetec.crns.core.service.notification.pushnotification.fcm;

import java.util.Collection;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.UserDevice;

public interface FCMService 
{
	public void sendNotification(Collection<UserDevice> devices, Notification notification);
}
