package com.intimetec.crns.core.mail.services;

import com.intimetec.crns.core.mail.commands.SendMailCommand;

public interface MailService 
{
	public void send(SendMailCommand command);
}
