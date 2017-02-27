package com.intimetec.crns.core.restmodels;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intimetec.crns.core.models.UserNotificationOptions;
import com.intimetec.crns.core.models.UserRole;

/**
 * Rest model class for User table
 * @author shiva.dixit
 */
public class RestUser {
	private long id;

	private String firstName;

	private String lastName;

	private String email;
	
	private String mobileNo;

	private String userName;

	private String password;

	private boolean enabled;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	private RestLocation location;

	private UserNotificationOptions userNotificationOptions;
	
	private boolean accountSetupDone = false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public UserNotificationOptions getUserNotificationOptions() {
		return userNotificationOptions;
	}

	public void setUserNotificationOptions(UserNotificationOptions userNotificationOptions) {
		this.userNotificationOptions = userNotificationOptions;
	}

	public RestLocation getLocation() {
		return this.location;
	}

	public void setLocation(RestLocation location) {
		this.location = location;
	}

	public boolean isAccountSetupDone(){
		return this.accountSetupDone;
	}
	
	public void setAccountSetupDone(){
		this.accountSetupDone = (userNotificationOptions == null) ? false : true;
	}
	
	public RestUser(){
		setAccountSetupDone();
	}

	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", username=" + userName +
                ", email=" + email +
                ", mobileNo=" + mobileNo +
                ", role=" + userRole +
                ", password=" + password +
                ", userNotificationOptions=" + userNotificationOptions +
                ", Location=" + location +
                '}';
    }
}
