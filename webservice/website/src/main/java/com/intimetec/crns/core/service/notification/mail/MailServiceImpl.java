package com.intimetec.crns.core.service.notification.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.config.MailConfig;
import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;


@Service
public class MailServiceImpl implements MailService
{
	/**
	 * To log the application messages. 
	 */
	private static final Logger LOGGER = LoggerFactory.
			getLogger(MailServiceImpl.class);
	
	@Autowired
	private MailConfig mailConfig;
	private Transport t;

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

			StringBuilder addresses = new StringBuilder();
			if(user.getUserNotificationOptions().isSendEmail() && user.getEmail()!=null && !user.getEmail().isEmpty()) {
				addresses.append(user.getEmail()+", ");
			}
			if(user.getUserNotificationOptions().isSendSms() && user.getMobileNo()!=null && !user.getMobileNo().isEmpty()) {
				addresses.append(user.getMobileNo()+"@txt.att.net, ");
				addresses.append(user.getMobileNo()+"@tmomail.net, ");
				addresses.append(user.getMobileNo()+"@messaging.sprintpcs.com,");
				addresses.append(user.getMobileNo()+"@vtext.com ");
			}
			 
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mailConfig.getUserName()"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(addresses.toString()));
			message.setSubject(notification.getSubject());
			message.setText(notification.getMessage());

			Transport.send(message);
			
			t = session.getTransport("smtp");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				t.close();
			} catch (MessagingException e) {
				LOGGER.warn("Unable to close Transport connection");
				e.printStackTrace();
			}
		}
	}
}
