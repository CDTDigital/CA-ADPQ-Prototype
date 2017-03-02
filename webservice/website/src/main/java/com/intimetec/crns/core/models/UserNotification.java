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
 * Model class for User notifications.
 * @author In Time Tec
 *
 */

@Entity
@Table(name = "user_notification")
public class UserNotification {
	/**
	 * Id of the User notification.
	 */
	@Id
	@GeneratedValue
	@Column(name = "user_notification_id")
	private long id;
	
	/**
	 * Instance of the class {@code Notification}.
	 */
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "notification_id")
	private Notification notification;
	
	/**
	 * Id of the User.
	 */
	@Column(name = "user_id")
	private long userId;
	
	/**
	 * Status of the notification whether it's read or unread.
	 */
	@Column(name = "is_read")
	private boolean read = false;

	/**
	 * @return Id.
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id the id of the user notification.
	 */
	public final void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return the notification.
	 */
	public final Notification getNotification() {
		return notification;
	}

	/**
	 * @param notification the notification of the user.
	 */
	public final void setNotification(final Notification notification) {
		this.notification = notification;
	}

	/**
	 * @return the id of the user.
	 */
	public final long getUserId() {
		return userId;
	}

	/**
	 * @param userId the id of the User.
	 */
	public final void setUserId(final long userId) {
		this.userId = userId;
	}

	/**
	 * @return true if the notification is read.
	 */
	public final boolean isRead() {
		return read;
	}

	/**
	 * @param read the status of the notification.
	 */
	public final void setRead(final boolean read) {
		this.read = read;
	}
	
	@Override
	public final String toString() {
		return "UserNotification{"
				+ "id: " + id
				+ ", notification: " + notification
				+ ", userId: " + userId
				+ ", isRead: " + read;
	}
	
	public UserNotification(){
		
	}
	
	public UserNotification(long userId, Notification notifcation){
		this.userId = userId;
		this.notification = notifcation;
	}
}
