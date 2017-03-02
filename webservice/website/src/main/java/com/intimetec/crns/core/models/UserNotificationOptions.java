package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for User Notification Options table.
 * @author In Time Tec
 */

@Entity
@Table(name = "user_notification_option")
public class UserNotificationOptions {
	/**
	 * Id of the User.
	 */
	@Id
	@Column(name = "user_id")
	private long userId;
	
	/**
	 * Option for the SMS service.
	 */
	@Column(name = "send_sms")
	private boolean sendSms;
	
	/**
	 * Option for the Email service.
	 */
	@Column(name = "send_email")
	private boolean sendEmail;
	
	/**
	 * Option for the Push notification service.
	 */
	@Column(name = "send_push_notification")
	private boolean sendPushNotification;
	
	/**
	 * Live Location tracking option.
	 */
	@Column(name = "live_location_tracking")
	private boolean liveLocationTracking;

	/**
	 * @return the id of the User.
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
	 * @return true if the SMS service is opt in.
	 */
	public final boolean isSendSms() {
		return sendSms;
	}

	/**
	 * @param sendSms option for the SMS service.
	 */
	public final void setSendSms(boolean sendSms) {
		this.sendSms = sendSms;
	}

	/**
	 * @return true if the Email service is opt in.
	 */
	public final boolean isSendEmail() {
		return sendEmail;
	}

	/**
	 * @param sendEmail option for the Email service.
	 */
	public final void setSendEmail(final boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	/**
	 * @return true if the Push notification service is opt in.
	 */
	public final boolean isSendPushNotification() {
		return sendPushNotification;
	}

	/**
	 * @param sendPushNotification option for the Push notification service.
	 */
	public final void setSendPushNotification(
			final boolean sendPushNotification) {
		this.sendPushNotification = sendPushNotification;
	}

	/**
	 * @return true if the Live location tracking service is opt in.
	 */
	public final boolean isLiveLocationTracking() {
		return liveLocationTracking;
	}

	/**
	 * @param liveLocationTracking option for the Live tracking service.
	 */
	public final void setLiveLocationTracking(
			final boolean liveLocationTracking) {
		this.liveLocationTracking = liveLocationTracking;
	}
}
