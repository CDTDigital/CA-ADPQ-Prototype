/******************************************************************************************
*
* Script for Database tables for CRNS (California Residents Notification Service)
*
*******************************************************************************************/

-- Table for user Login
create table user (
	user_id INT NOT NULL AUTO_INCREMENT,
	first_name NVARCHAR(50) NULL,
	last_name NVARCHAR(50) NULL,
	email NVARCHAR(100) NOT NULL,
	mobile_no NVARCHAR(15) NULL,
	username NVARCHAR(16) NOT NULL,
	password NVARCHAR(100) NOT NULL,
	enabled BINARY DEFAULT 1,
	role NVARCHAR(10) NOT NULL,
	CONSTRAINT pk_userLogin PRIMARY KEY (user_id),
	CONSTRAINT unique_user_name UNIQUE INDEX (username),
	CONSTRAINT unique_user_email UNIQUE INDEX (email)
);

-- Table for User Location
create table user_locations (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	address_line1 NVARCHAR(100),
	address_line2 NVARCHAR(100),
	city NVARCHAR(100),
	zip_code NVARCHAR(100) NOT NULL,
	place_id NVARCHAR(200) NOT NULL,
	latitude NVARCHAR(30) NOT NULL,
	longitude NVARCHAR(30) NOT NULL,
	current_location BOOLEAN NOT NULL DEFAULT 0,
	CONSTRAINT pk_user PRIMARY KEY (id),
	CONSTRAINT fk_userInfo_userLogin FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Table for user notification options
create table user_notification_option (
	user_id INT NOT NULL,
	send_sms BOOLEAN NOT NULL DEFAULT 0,
	send_email BOOLEAN NOT NULL DEFAULT 0,
	send_push_notification BOOLEAN NOT NULL DEFAULT 0,
	live_location_tracking BOOLEAN NOT NULL DEFAULT 0,
	CONSTRAINT pk_userNotificationOption PRIMARY KEY (user_id),
	CONSTRAINT fk_userNotificationOption_user FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Table for User Device
create table user_device (
	id INT  NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	device_id NVARCHAR(100) NOT NULL,
	device_token NVARCHAR(250) NOT NULL,
	device_type NVARCHAR(15) NOT NULL,
	auth_token NVARCHAR(150) UNIQUE,
	CONSTRAINT pk_userDevice PRIMARY KEY (id),
	CONSTRAINT fk_userDevice_user FOREIGN KEY (user_id) REFERENCES user(user_id),
	CONSTRAINT unique_userDevice_user UNIQUE INDEX (user_id, device_id)
);

-- Table for Notification
create table notification (
	notification_id INT NOT NULL AUTO_INCREMENT,
	sent_by INT NOT NULL,
	subject NVARCHAR(100) NOT NULL, 
	message NVARCHAR(1000) NOT NULL,
	city NVARCHAR(100) NOT NULL,
	zip_code NVARCHAR(100) NOT NULL,
    address NVARCHAR(500) NOT NULL,
	latitude NVARCHAR(30) NOT NULL,
	longitude NVARCHAR(30) NOT NULL,
	sent_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	valid_through TIMESTAMP NOT NULL,
	CONSTRAINT pk_notification PRIMARY KEY (notification_id),
	CONSTRAINT fk_sentBy_user FOREIGN KEY (sent_by) REFERENCES user(user_id)
);

-- Table for users who received the notification
create table user_notification (
	user_notification_id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	notification_id INT NOT NULL,
	is_read BOOLEAN NOT NULL DEFAULT 0,
	CONSTRAINT pk_user_notification PRIMARY KEY (user_notification_id),
	CONSTRAINT fk_userNotification_user FOREIGN KEY (user_id) REFERENCES user(user_id),
	CONSTRAINT fk_userNotification_notification FOREIGN KEY (notification_id) REFERENCES notification(notification_id),
	CONSTRAINT unique_userNotification UNIQUE INDEX (user_id, notification_id)
);