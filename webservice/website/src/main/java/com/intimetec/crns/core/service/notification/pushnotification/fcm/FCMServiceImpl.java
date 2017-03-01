package com.intimetec.crns.core.service.notification.pushnotification.fcm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.intimetec.crns.core.config.fcm.FcmConfig;
import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.models.fcm.AndroidPayload;
import com.intimetec.crns.core.models.fcm.IOSPayload;

@Service
public class FCMServiceImpl implements FCMService
{
	@Autowired
	private FcmConfig fcmConfig;
	
	@Async
	public void sendNotification(Collection<UserDevice> devices, Notification notification)
	{
		final String uri = fcmConfig.getUrl();
		List<String> androidDeviceIdList = new ArrayList<String>();
		List<String> iosDeviceIdList = new ArrayList<String>();
		for(UserDevice device: devices){
			if(device.getDeviceType().equalsIgnoreCase("android")){
				androidDeviceIdList.add(device.getDeviceToken());
			} 
			else if(device.getDeviceType().equalsIgnoreCase("ios")){
				iosDeviceIdList.add(device.getDeviceToken());
			}
		}
		 
		AndroidPayload androidPayLoad = new AndroidPayload(androidDeviceIdList, 
				notification.getSubject(), notification.getMessage(), notification.getId(), notification.getId());
		
		IOSPayload iosPayLoad = new IOSPayload(iosDeviceIdList, notification.getSubject(), 
				notification.getMessage(), notification.getId());
				 
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.postForObject( uri, androidPayLoad, AndroidPayload.class);
	    
	    restTemplate.postForObject( uri, iosPayLoad, IOSPayload.class);

	}
}
