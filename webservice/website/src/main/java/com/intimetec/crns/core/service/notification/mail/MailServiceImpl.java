package com.intimetec.crns.core.service.notification.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.config.MailConfig;
import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;

@Service
public class MailServiceImpl implements MailService
{
	@Autowired
	private MailConfig mailConfig;

	public MailServiceImpl(MailConfig mailConfig)
	{
		this.mailConfig = mailConfig;
	}
	
	@Async
	public void sendMailToUsers(User user, Notification notification)
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", mailConfig.getHost());
		props.put("mail.smtp.socketFactory.port", mailConfig.getPort());
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", mailConfig.getPort());

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailConfig.getUserName(),mailConfig.getPassword());
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mailConfig.getUserName()"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(user.getEmail()));
			message.setSubject(notification.getSubject());
			message.setText(notification.getMessage());

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}