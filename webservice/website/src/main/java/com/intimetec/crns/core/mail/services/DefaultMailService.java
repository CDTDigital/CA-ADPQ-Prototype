package com.intimetec.crns.core.mail.services;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.intimetec.crns.core.mail.commands.SendMailCommand;

public class DefaultMailService implements MailService
{
	private final MailSender mailSender;
	
	public DefaultMailService(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	@Override
	public void send(SendMailCommand command)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(command.getTo());
		message.setFrom(command.getFrom());
		message.setSubject(command.getSubject());
		message.setText(command.getBody());
		
		this.mailSender.send(message);		
	}
	
	/*@Override
	public void send()
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("bincyysamuelissnt@gmail.com");
		message.setReplyTo("bincyysamuelissnt@gmail.com");
		message.setFrom("bincy.samuel@intimetec.com");
		message.setCc("");
		message.setBcc("");
		message.setSentDate(null);
		message.setSubject("Test");
		message.setText("Sample");
		
		System.out.println(message);
		this.mailSender.send(message);		
		System.out.println("Success");
	}*/
}
