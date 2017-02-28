package com.intimetec.crns.core.service.notification.pushnotification.fcm;

import java.util.Collection;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.UserDevice;

@Service
public class FCMServiceImpl implements FCMService
{
	
	@Async
	public void sendNotification(Collection<UserDevice> devices, Notification notification)
	{
		final String uri = "http://localhost:8080/springrestexample/employees";
		 
	   /* EmployeeVO newEmployee = new EmployeeVO(-1, "Adam", "Gilly", "test@email.com");
	 
	    RestTemplate restTemplate = new RestTemplate();
	    EmployeeVO result = restTemplate.postForObject( uri, newEmployee, EmployeeVO.class);*/
	 
	    //System.out.println(result);
	}
}
