package com.intimetec.crns.core.service.notification.mail;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;

/**
 * {@code MailService} for sending mails to the users.
 * @author shiva.dixit
 */
public interface MailService 
{
	public void sendMailToUsers(User user, Notification notification);
}
