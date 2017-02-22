/*************************************************************************
*
* Insert Default Admin User (demo-admin@gmail.com)
* 		and Insert Default User (demo-user@gmail.com)
*
**************************************************************************/
INSERT INTO user (first_name, last_name, email, role, username, password, status)
	VALUES ('CRNS Demo', 'Admin', 'demo.crns@gmail.com', 'ADMIN', 'admin', '$2a$10$va6WE55FcZQrChb3uMutSugrrfW9K6.TFFHag3xwbbEWH/zRop5R6', 1),
			('CRNS Demo', 'User', 'crns.demouser@gmail.com', 'USER', 'user', '$2a$10$RoZnolmb64akVPHfbKIE8Ow1Vgsa0/qi13lk3EvTkbfI771AHUAf6', 1);

INSERT INTO user_notification_option (user_id, send_sms, send_email, send_push_notification, live_location_tracking)
	VALUES (1, 0, 0, 0, 0),
			(2, 1, 1, 1, 1)