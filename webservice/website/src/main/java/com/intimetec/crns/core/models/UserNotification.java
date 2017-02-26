/**
 * 
 */
package com.intimetec.crns.core.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * Model class for User notifications
 * @author shiva.dixit
 *
 */

@Entity
@Table(name="user_notification")
public class UserNotification {
	@Id
	@GeneratedValue
	@Column(name="user_notification_id")
	private long id;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	@JoinColumn(name="notification_id")
	private Notification notification;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="is_read")
	private boolean read;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	@Override
	public String toString() {
		return "UserNotification{"
				+"id: "+id
				+", notification: "+notification
				+", userId: "+userId
				+", isRead: "+read;

	}
}
