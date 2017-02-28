package com.intimetec.crns.core.restmodels;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.intimetec.crns.core.models.UserNotificationOptions;
import com.intimetec.crns.core.models.UserRole;

/**
 * Rest model class for User table.
 * @author shiva.dixit
 */
public class RestUser {
	/**
	 * Id of the User.
	 */
	private long id;

	/**
	 * First name of the User.
	 */
	private String firstName;

	/**
	 * Last name of the User.
	 */
	private String lastName;

	/**
	 * Email of the User.
	 */
	private String email;
	
	/**
	 * Mobile number of the User.
	 */
	private String mobileNo;

	/**
	 * User name of the User.
	 */
	private String userName;

	/**
	 * Password of the User.
	 */
	private String password;

	/**
	 * Status of the User.
	 */
	private boolean enabled;

	/**
	 * Role of the User.
	 */
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	/**
	 * Location of the User.
	 */
	private RestLocation location;

	/**
	 * Notification options for the User.
	 */
	private UserNotificationOptions userNotificationOptions;
	
	/**
	 * Status of the Account set up.
	 */
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
	public final void setEnabled(final boolean enabled) {
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
	 * @return the location of the rest User.
	 */
	public final RestLocation getLocation() {
		return this.location;
	}

	/**
	 * @param location the location of the rest User.
	 */
	public final void setLocation(final RestLocation location) {
		this.location = location;
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
	public boolean setAccountSetupDone(boolean accountSetupDone){
		return this.accountSetupDone = accountSetupDone;
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
                + ", Location=" + location 
                + '}';
    }
}
