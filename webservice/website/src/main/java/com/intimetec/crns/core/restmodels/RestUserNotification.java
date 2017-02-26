package com.intimetec.crns.core.restmodels;

/**
 * 
 * Model class for User notifications.
 * @author shiva.dixit
 *
 */

public class RestUserNotification {
	private long id;

	private RestNotification notification;
	
	private long userId;

	private boolean read;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RestNotification getNotification() {
		return notification;
	}

	public void setNotification(RestNotification notification) {
		this.notification = notification;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(final long userId) {
		this.userId = userId;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(final boolean read) {
		this.read = read;
	}
	
	@Override
	public final String toString() {
		return "RestUserNotification{"
				+ "id: " + id
				+ ", notification: " + notification
				+ ", userId: " + userId
				+ ", isRead: " + read;

	}
}
