package com.intimetec.crns.core.models;

import java.util.Date;

import javax.persistence.CascadeType;
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
 * Entity model class for Notification table.
 * @author shiva.dixit
 */
@Entity
@Table(name = "notification")
public class Notification {
	/**
	 * Id of the notification.
	 */
	@Id

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="notification_id")
	private long id;
	
	/**
	 * User who will be sending the notification.
	 */
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	@JoinColumn(name="sent_by", referencedColumnName="user_id")
	private User sentBy;
	
	/**
	 * Subject of the notification.
	 */
	@Column(name = "subject")
	private String subject;

	/**
	 * Message to be sent in the notification.
	 */
	@Column(name = "message")
	private String message;
	
	/**
	 * City where the notification will be sent.
	 */
	@Column(name = "city")
	private String city;
	
	/**
	 * Zip code on whose basis the notification will be sent.
	 */
	@Column(name = "zip_code")
	private String zipCode;
	
	/**
	 * Address where the notification will be sent.
	 */
	@Column(name = "address")
	private String address;

	/**
	 * Time when the notification will be sent.
	 */
	@Column(name = "sent_time")
	private Date sentTime;
	
	/**
	 * The Date and time till when the notification is valid.
	 */
	@Column(name = "valid_through")
	private Date validThrough;

	/**
	 * @return id.
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id the id of the notification.
	 */
	public final void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return sentBy the User.
	 */
	public final User getSentBy() {
		return sentBy;
	}

	/**
	 * @param sentBy the User .
	 */
	public final void setSentBy(final User sentBy) {
		this.sentBy = sentBy;
	}

	/**
	 * @return the subject of the notification.
	 */
	public final String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject of the notification..
	 */
	public final void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return message of the notification.
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @param message of the notification.
	 */
	public final void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * @return city where the notification will be sent.
	 */
	public final String getCity() {
		return city;
	}

	/**
	 * @param city.
	 */
	public final void setCity(final String city) {
		this.city = city;
	}

	/**
	 * @return zipCode.
	 */
	public final String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zip code.
	 */
	public final void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return address.
	 */
	public final String getAddress() {
		return address;
	}

	/**
	 * @param address.
	 */
	public final void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return Date.
	 */
	public final Date getSentTime() {
		return sentTime;
	}

	/**
	 * @param sentTime the Date and time.
	 */
	public final void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	/**
	 * @return the date and time.
	 */
	public final Date getValidThrough() {
		return validThrough;
	}

	/**
	 * @param validThrough.
	 */
	public final void setValidThrough(final Date validThrough) {
		this.validThrough = validThrough;
	}
}
