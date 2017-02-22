package com.intimetec.crns.core.mail.commands;

public class SendMailCommand
{
	public final String to;
	public final String from;
	public final String subject;
	public final String body;
	
	public SendMailCommand(String to, String from, String subject, String body)
	{
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.body = body;
	}

	public String getTo()
	{
		return this.to;
	}

	public String getFrom()
	{
		return this.from;
	}

	public String getSubject()
	{
		return this.subject;
	}

	public String getBody()
	{
		return this.body;
	}
}
