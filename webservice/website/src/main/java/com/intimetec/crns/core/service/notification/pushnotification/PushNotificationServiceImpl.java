package com.intimetec.crns.core.service.notification.pushnotification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.models.UserNotification;
import com.intimetec.crns.core.service.notification.pushnotification.fcm.FCMService;
import com.intimetec.crns.core.service.userdevice.UserDeviceService;


/**
 * @author In Time Tec
 *
 */
@Service
public class PushNotificationServiceImpl implements PushNotificationService {
	@Autowired
	private final UserDeviceService userDeviceService;
	@Autowired
	private final FCMService fcmService;
	

	public PushNotificationServiceImpl(UserDeviceService userDeviceService, 
			FCMService fcmService) {
		this.userDeviceService = userDeviceService;
		this.fcmService = fcmService;
	}
	
	@Async
	public void sendNotificationToUsers(Collection<User> users, Notification notification)
	{
		List<UserNotification> userNotifications = new ArrayList<UserNotification>();
		for(User user:users){
			Collection<UserDevice> userDevices = userDeviceService.getUserDevicesByUserId(user.getId());
			userNotifications.add(new UserNotification(user.getId(), notification));
			fcmService.sendNotification(userDevices, notification);
		}
	}
}
