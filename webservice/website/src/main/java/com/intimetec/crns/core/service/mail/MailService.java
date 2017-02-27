package com.intimetec.crns.core.service.mail;

import java.util.Collection;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;

public interface MailService 
{
	public void sendMailToUsers(Collection<User> users, Notification notification);
}
