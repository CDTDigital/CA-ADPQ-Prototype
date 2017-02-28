package com.intimetec.crns.core.models;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity model class for User table.
 * @author shiva.dixit
 */
@Entity
@Table(name = "user")
public class User {
	/**
	 * Id of the User.
	 */
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private long id;
	
	/**
	 * First name of the User.
	 */
	@Column(name = "first_name")
	private String firstName;

	/**
	 * Last name of the User.
	 */
	@Column(name = "last_name")
	private String lastName;

	/**
	 * Email of the User.
	 */
	@Column(name = "email")
	private String email;

	/**
	 * Mobile number of the User.
	 */
	@Column(name = "mobile_no")
	private String mobileNo;

	/**
	 * User name of the User.
	 */
	@Column(name = "username")
	private String userName;

	/**
	 * Password of the User.
	 */
	@Column(name = "password")
	private String password;

	/**
	 * Status of the User.
	 */
	@Column(name = "enabled")
	private boolean enabled;

	/**
	 * Role of the User.
	 */
	@JsonProperty(value="role")
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	/**
	 * Notification options for the User.
	 */
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserNotificationOptions userNotificationOptions;

	/**
	 * Status of the Account set up.
	 */
	@Column(name="account_setup_done")
	private boolean accountSetupDone = false;

	/**
	 * @return id of the User.
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id the id of the User.
	 */	
	public final void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return the first name of the User.
	 */
	public final String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the first name of the User.
	 */	
	public final void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the last name of the User.
	 */
	public final String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the last name of the User.
	 */
	public final void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email of the User.
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email the email of the User.
	 */
	public final void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the mobile number of the User.
	 */
	public final String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobile number of the User.
	 */
	public final void setMobileNo(final String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the user name of the User.
	 */
	public final String getUserName() {
		return userName;
	}

	/**
	 * @param userName the user name of the User.
	 */
	public final void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password of the User.
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password the password of the User.
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the status of the User.
	 * Returns true if the User is enabled.
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the status of the User.
	 */
	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the role of the User.
	 */
	public final UserRole getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the role of the User.
	 */
	public final void setUserRole(final UserRole userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the notification options for the User.
	 */
	public final UserNotificationOptions getUserNotificationOptions() {
		return userNotificationOptions;
	}

	/**
	 * @param userNotificationOptions the notification options for the User.
	 */
	public final void setUserNotificationOptions(
			final UserNotificationOptions userNotificationOptions) {
		this.userNotificationOptions = userNotificationOptions;
	}

	/**
	 * @return the status of the account set up.
	 * Returns true if the account has been set up.
	 */
	public final boolean isAccountSetupDone() {
		return this.accountSetupDone;
	}
	
	/**
	 * Method for the account set up.
	 */
	public final void setAccountSetupDone() {
		this.accountSetupDone = (
				userNotificationOptions == null) ? false : true;
	}
	
	/**
	 * Creating object of the class{@code User}.
	 */
	public User() {
		setAccountSetupDone();
	}

	@Override
	public final String toString() {
        return "User{" 
               + "id=" + id 
               + ", firstName=" + firstName 
               + ", lastName=" + lastName 
               + ", username=" + userName 
               + ", email=" + email 
               + ", mobileNo=" + mobileNo 
               + ", role=" + userRole 
               + ", password=" + password 
               + ", userNotificationOptions=" + userNotificationOptions 
               + '}';
    }
}
