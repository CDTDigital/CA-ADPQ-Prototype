/******************************************************************************************
*
* Script for Database tables for CRNS (California Residents Notification Service)
*
*******************************************************************************************/

-- Table for User Information
create table user (
	user_id INT NOT NULL AUTO_INCREMENT,
	first_name NVARCHAR(50) NOT NULL,
	last_name NVARCHAR(50) NOT NULL,
	email NVARCHAR(100) NOT NULL,
	address_line1 NVARCHAR(100) NOT NULL,
	address_line2 NVARCHAR(100),
	city NVARCHAR(100) NOT NULL,
	zip_code NVARCHAR(100) NOT NULL,
	role NVARCHAR(10) NOT NULL,	
	CONSTRAINT pk_user PRIMARY KEY (user_id)
);

-- Table for User Login
create table user_login (
	user_id INT NOT NULL,
	password NVARCHAR(15) NOT NULL,
	status BINARY DEFAULT 1,
	CONSTRAINT pk_userLogin PRIMARY KEY (user_id),
	CONSTRAINT fk_userLogin_user FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Table for mobile device information
create table device_info (
	devinfo_id INT NOT NULL AUTO_INCREMENT,
	device_id NVARCHAR(100) NOT NULL,
	device_token NVARCHAR(250) NOT NULL UNIQUE,
	device_type NVARCHAR(15) NOT NULL,
	CONSTRAINT pk_devInfo PRIMARY KEY (devinfo_id),
	CONSTRAINT unique_devInfo UNIQUE (device_id, device_token)
);

-- Table for User Device
create table user_device (
	id INT  NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	devinfo_id INT NOT NULL,
	auth_token NVARCHAR(25) UNIQUE,
	CONSTRAINT pk_userDevice PRIMARY KEY (id),
	CONSTRAINT fk_userDevice_user FOREIGN KEY (user_id) REFERENCES user(user_id),
	CONSTRAINT fk_userDevice_device FOREIGN KEY (devinfo_id) REFERENCES device_info(devinfo_id)
);

-- Table for Notification
create table notification (
	notification_id INT NOT NULL AUTO_INCREMENT,
	sent_by INT NOT NULL,
	subject NVARCHAR(100) NOT NULL, 
	message NVARCHAR(1000) NOT NULL,
	city NVARCHAR(100) NOT NULL,
	zip_code NVARCHAR(100) NOT NULL,
	date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT pk_notification PRIMARY KEY (notification_id),
	CONSTRAINT fk_sentBy_user FOREIGN KEY (sent_by) REFERENCES user(user_id)
);
