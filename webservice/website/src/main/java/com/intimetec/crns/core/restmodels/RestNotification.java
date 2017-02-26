package com.intimetec.crns.core.restmodels;

import java.util.Date;

/**
 * Rest model class for Notification
 * @author shiva.dixit
 */
public class RestNotification {
	private long id;
	
	private User sentBy;
	
	private String subject;

	private String message;
	
	private String city;
	
	private String zipCode;
	
	private String address;

	private Date sentTime;
	
	private Date validThrough;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	
	public void setUserInfo(String firstName, String lastName, String email){
		this.sentBy = new User(firstName, lastName, email);
		
	}
	
	public class User {
		private String firstName;

		private String lastName;

		private String email;
		
		public User(String firstName, String lastName, String email){
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getEmail() {
			return email;
		}
	}
}
