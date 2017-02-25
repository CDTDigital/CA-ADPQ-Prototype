package com.intimetec.crns.core.models;

import java.util.Date;

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
 * Entity model class for User table
 * @author shiva.dixit
 */
@Entity
@Table(name="notification")
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="notification_id")
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sent_by")
	private User sentBy;
	
	@Column(name="subject")
	private String subject;

	@Column(name="message")
	private String message;
	
	@Column(name="city")
	private String city;
	
	@Column(name="zip_code")
	private String zipCode;
	
	@Column(name="address")
	private String address;

	@Column(name="sent_time")
	private Date sentTime;
	
	@Column(name="valid_through")
	private Date validThrough;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getSentBy() {
		return sentBy;
	}

	public void setSentBy(User sentBy) {
		this.sentBy = sentBy;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	public Date getValidThrough() {
		return validThrough;
	}

	public void setValidThrough(Date validThrough) {
		this.validThrough = validThrough;
	}
}
