/*************************************************************************
*
* Insert Default Admin User (demo-admin@gmail.com)
* 		and Insert Default User (demo-user@gmail.com)
*
**************************************************************************/
INSERT INTO user (first_name, last_name, email, mobile_no, role, username, password, enabled)
	VALUES ('CRNS Demo', 'Admin', 'demo.crns@gmail.com', '7891166332', 'ADMIN', 'admin', '$2a$10$ALK50Hl7beetI1ksU/lTJOgte0ro7QY6.3/sw/nAyBqSTTEoPbZLq', 1),
			('CRNS Demo', 'User', 'crns.demouser@gmail.com', '7891166332', 'USER', 'user', '$2a$10$RoZnolmb64akVPHfbKIE8Ow1Vgsa0/qi13lk3EvTkbfI771AHUAf6', 1);

INSERT INTO user_notification_option (user_id, send_sms, send_email, send_push_notification, live_location_tracking)
	VALUES (1, 0, 0, 0, 0),
			(2, 1, 1, 1, 1)