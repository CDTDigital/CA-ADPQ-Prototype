package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity model class for User table
 * @author shiva.dixit
 */
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private long id;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="email")
	private String email;

	@Column(name="username")
	private String userName;

	@JsonIgnore
	@Column(name="password")
	private String password;

	@Column(name="status")
	private boolean status;

	@JsonIgnore
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
	
	public boolean isAccountSetupDone(){
		return this.accountSetupDone;
	}
	
	public void setAccountSetupDone(){
		this.accountSetupDone = (userNotificationOptions == null) ? false : true;
	}
	
	public User(){
		setAccountSetupDone();
	}

	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + userName +
                ", email='" + email +
                ", role=" + userRole +
                '}';
    }
}
