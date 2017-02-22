package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for User Notification Options table
 * @author shiva.dixit
 */

@Entity
@Table(name="user_notification_option")
public class UserNotificationOptions {
	@Id
	@Column(name="user_id")
	private long userId;
	
	@Column(name="send_sms")
	private boolean sendSms;
	
	@Column(name="send_email")
	private boolean sendEmail;
	
	@Column(name="send_push_notification")
	private boolean sendPushNotification;
	
	@Column(name="live_location_tracking")
	private boolean liveLocationTracking;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isSendSms() {
		return sendSms;
	}

	public void setSendSms(boolean sendSms) {
		this.sendSms = sendSms;
	}

	public boolean isSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public boolean isSendPushNotification() {
		return sendPushNotification;
	}

	public void setSendPushNotification(boolean sendPushNotification) {
		this.sendPushNotification = sendPushNotification;
	}

	public boolean isLiveLocationTracking() {
		return liveLocationTracking;
	}

	public void setLiveLocationTracking(boolean liveLocationTracking) {
		this.liveLocationTracking = liveLocationTracking;
	}
}
