package com.intimetec.crns.core.restmodels;

import java.util.Date;

/**
 * Rest model class for the Notification table.
 * @author shiva.dixit
 */
public class RestNotification {
	/**
	 * Id of the notification.
	 */
	private long id;
	
	/**
	 * User who will be sending the notification.
	 */
	private User sentBy;
	
	/**
	 * Subject of the notification.
	 */
	private String subject;

	/**
	 * Message to be sent in the notification.
	 */
	private String message;
	
	/**
	 * City where the notification will be sent.
	 */
	private String city;
	
	/**
	 * Zip code on whose basis the notification will be sent.
	 */
	private String zipCode;
	
	/**
	 * Address where the notification will be sent.
	 */
	private String address;
	
	/**
	 * Latitude of the User's location.
	 */
	private String latitude;

	/**
	 * Longitude of the User's location.
	 */
	private String longitude;

	/**
	 * Time when the notification will be sent.
	 */
	private Date sentTime;
	
	/**
	 * The Date and time till when the notification is valid.
	 */
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

	/**
	 * @return the latitude of the Notification's location.
	 */
	public final String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude of the Notification's location.
	 */
	public final void setLatitude(final String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @return the longitude of the Notification's location.
	 */
	public final String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude of the Notification's location.
	 */
	public final void setLongitude(final String longitude) {
		this.longitude = longitude;
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
