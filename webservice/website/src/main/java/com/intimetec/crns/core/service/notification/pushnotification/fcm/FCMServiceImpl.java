package com.intimetec.crns.core.service.notification.pushnotification.fcm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
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
	@Autowired
	private Gson gson;
	
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
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "key=" +fcmConfig.getKey());
		headers.add("project_id", fcmConfig.getProjectKey());
		headers.add("Content-Type", "application/json");

		RestTemplate restTemplate = new RestTemplate();
		/*restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());*/
		 
		if(!androidDeviceIdList.isEmpty()) {
			AndroidPayload androidPayLoad = new AndroidPayload(androidDeviceIdList, 
					notification.getSubject(), notification.getMessage(), notification.getId(), notification.getId());
			
			HttpEntity<String> requestAndroid = new HttpEntity<String>(gson.toJson(androidPayLoad), headers);
			
			System.out.println("Android Payload : "+requestAndroid);

			String result = restTemplate.postForObject(uri, requestAndroid, String.class);
			
			System.out.println("Android Upload done: "+result);
		}
		
		if(!iosDeviceIdList.isEmpty()) {
			IOSPayload iosPayLoad = new IOSPayload(iosDeviceIdList, notification.getSubject(), 
					notification.getMessage(), notification.getId());
			
			HttpEntity<String> requestIos = new HttpEntity<String>(gson.toJson(iosPayLoad), headers);
			
			System.out.println("Android Payload : "+requestIos);

			String result = restTemplate.postForObject(uri, requestIos, String.class);
			
			System.out.println("IOS Upload done: "+result);
		}
	}
}
