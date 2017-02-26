package com.intimetec.crns.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;

import com.intimetec.crns.core.mail.services.DefaultMailService;
import com.intimetec.crns.core.mail.services.MailService;

public class DefaultServiceConfiguration implements ServiceConfiguration
{
	@Autowired
	private MailSender mailSender;
	
	@Bean
	@Override
	public MailService mailService()
	{
		return new DefaultMailService(this.mailSender);
	}
}
