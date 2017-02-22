/**
 * 
 */
package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_notification_id")
	private int id;
	
	@Column(name="notification_id")
	private int notificationId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User userId;
	
	@Column(name="is_read")
	private boolean read;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}
