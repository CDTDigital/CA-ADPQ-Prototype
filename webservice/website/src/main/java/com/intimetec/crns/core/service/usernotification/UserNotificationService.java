package com.intimetec.crns.core.service.usernotification;

import java.util.Collection;
import java.util.Optional;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.exceptions.InvalidUserException;
import com.intimetec.crns.core.models.UserNotification;

public interface UserNotificationService {
	Optional<UserNotification> getById(long id);

	Collection<UserNotification> getByUserId(long id);
	
	Collection<UserNotification> getByUserAuthToken(String authToken) throws InvalidAuthTokenException;

	Collection<UserNotification> getByNotificationId(long id);

	UserNotification save(UserNotification userNotification);
	
	Collection<UserNotification> getUserNotificationsByUserId(long id) throws InvalidUserException ;
	
	Collection<UserNotification> getUserNotificationsByAuthToken(String authToken) throws InvalidAuthTokenException, InvalidUserException ;
}
