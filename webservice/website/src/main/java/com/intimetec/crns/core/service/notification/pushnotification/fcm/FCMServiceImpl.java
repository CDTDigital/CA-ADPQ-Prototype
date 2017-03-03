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

/**
 * @author In Time Tec
 */
@Service
public class FCMServiceImpl implements FCMService {
	/**
	 * Instance of the class {@link FcmConfig}.
	 */
	@Autowired
	private FcmConfig fcmConfig;
	/**
	 * Instance of the class {@link Gson}.
	 */
	@Autowired
	private Gson gson;
	
	/**
	 * To send the notifications to the users.
	 * @param devices        the devices on which the notification 
	 *                       has to be sent.
	 * @param notification   the notification to be sent.
	 */
	@Async
	public void sendNotification(Collection<UserDevice> devices,
			final Notification notification) {
		final String uri = fcmConfig.getUrl();
		List<String> androidDeviceIdList = new ArrayList<String>();
		List<String> iosDeviceIdList = new ArrayList<String>();
		for (UserDevice device: devices) {
			if (device.getDeviceType().equalsIgnoreCase("android")) {
				androidDeviceIdList.add(device.getDeviceToken()); 
			} else if (device.getDeviceType().equalsIgnoreCase("ios")) {
				iosDeviceIdList.add(device.getDeviceToken());
			}
		}
		
		MultiValueMap<String, String> headers = new 
				LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "key=" + fcmConfig.getKey());
		headers.add("project_id", fcmConfig.getProjectKey());
		headers.add("Content-Type", "application/json");

		RestTemplate restTemplate = new RestTemplate();
		 
		if (!androidDeviceIdList.isEmpty()) {
			AndroidPayload androidPayLoad = new AndroidPayload(
					androidDeviceIdList, notification.getSubject(), 
					notification.getMessage(), notification.getId(), 
					notification.getId());
			
			HttpEntity<String> requestAndroid = new HttpEntity<String>(
					gson.toJson(androidPayLoad), headers);
			
			restTemplate.postForObject(uri, requestAndroid, String.class);
			
		}
		
		if (!iosDeviceIdList.isEmpty()) {
			IOSPayload iosPayLoad = new IOSPayload(
					iosDeviceIdList, notification.getSubject(), 
					notification.getMessage(), notification.getId());
			
			HttpEntity<String> requestIos = new HttpEntity<String>(
					gson.toJson(iosPayLoad), headers);

			restTemplate.postForObject(uri, requestIos, String.class);			
		}
	}
}
