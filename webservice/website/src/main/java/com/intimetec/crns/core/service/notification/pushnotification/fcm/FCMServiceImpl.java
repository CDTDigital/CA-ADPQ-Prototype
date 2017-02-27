package com.intimetec.crns.core.service.notification.pushnotification.fcm;

import java.util.Collection;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.UserDevice;

@Service
public class FCMServiceImpl implements FCMService
{
	
	@Async
	public void sendNotification(Collection<UserDevice> devices, Notification notification)
	{
		String url  = "https://fcm.googleapis.com/fcm/send";
		RestTemplate restTemplate= new RestTemplate();
	//	String result = restTemplate.
	}
}
