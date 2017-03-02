package com.intimetec.crns.core.service.usernotification;

import java.util.Optional;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.exceptions.InvalidUserException;
import com.intimetec.crns.core.models.UserNotificationOptions;

public interface UserNotificationOptionsService {
	Optional<UserNotificationOptions> getById(long id);

	Optional<UserNotificationOptions> getByUserId(long id);

	UserNotificationOptions getByAuthToken(String authToken) throws InvalidAuthTokenException;

	UserNotificationOptions save(UserNotificationOptions userNotificationOptions);

	UserNotificationOptions saveNotificationOptionsByUserId(long userId,
			UserNotificationOptions userNotificationOptions) throws InvalidUserException;

	UserNotificationOptions saveNotificationOptionsByAuthToken(String authToken,
			UserNotificationOptions userNotificationOptions) throws InvalidUserException;
}
