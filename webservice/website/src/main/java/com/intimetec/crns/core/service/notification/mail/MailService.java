package com.intimetec.crns.core.service.notification.mail;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;

public interface MailService 
{
	public void sendMailToUsers(User user, Notification notification);
}
