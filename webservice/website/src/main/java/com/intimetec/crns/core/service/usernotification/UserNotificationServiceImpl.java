package com.intimetec.crns.core.service.usernotification;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.exceptions.InvalidUserException;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserNotification;
import com.intimetec.crns.core.repository.UserNotificationRepository;
import com.intimetec.crns.core.service.user.UserService;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserNotificationServiceImpl.class);
    @Autowired
    private UserNotificationRepository userNotificationRepository;
    
    @Autowired
    private UserService userService;

    @Override
    public Optional<UserNotification> getById(long id) {
        LOGGER.debug("Getting user Notification ={}", id);
        return Optional.ofNullable(userNotificationRepository.findOne(id));
    }
    
    @Override
	public Collection<UserNotification> getByNotificationId(long notificationId) {
    	LOGGER.debug("Getting user Notifications by notificationId={}",notificationId);
        Collection<UserNotification> userNotification= userNotificationRepository.getByNotificationIdOrderByNotificationSentTimeDesc(notificationId);
        return userNotification;
	}

    @Override
    public Collection<UserNotification> getByUserId(long userId) {
       LOGGER.debug("Getting user Notifications  by userId={}",userId);
       Collection<UserNotification> userNotifications= userNotificationRepository.getByUserIdOrderByNotificationSentTimeDesc(userId);
       return userNotifications;
    }
    
    @Override
    public Collection<UserNotification> getByUserAuthToken(String authToken) throws InvalidAuthTokenException {
    	LOGGER.debug("Getting user Notifications  by user authToken={}",authToken);
    	Optional<User> user = userService.getValidUserForAuthToken(authToken);
    	if(user.isPresent()){
			return getByUserId(user.get().getId());
		} else {
			throw new InvalidAuthTokenException("Invalid User data");
		}
    }
    
    @Override
    public UserNotification save(UserNotification userNotification) {
    	LOGGER.debug("Saving user Notifications={}",userNotification);
        return userNotificationRepository.save(userNotification);
    }

	@Override
	public Collection<UserNotification> getUserNotificationsByUserId(long userId) throws InvalidUserException {
		LOGGER.debug("Getting user Notifications by user id={}",userId);
		return userNotificationRepository.getByUserIdOrderByNotificationSentTimeDesc(userId);
	}

	@Override
	public Collection<UserNotification> getUserNotificationsByAuthToken(String authToken) throws InvalidAuthTokenException, InvalidUserException {
		LOGGER.debug("Getting user Notifications  by user authToken={}",authToken);
    	Optional<User> user = userService.getValidUserForAuthToken(authToken);
    	if(user.isPresent()){
			return getUserNotificationsByUserId(user.get().getId());
		} else {
			throw new InvalidAuthTokenException("Invalid Auth Token");
		}
	}
	
	@Override
	public void save(Collection<UserNotification> notifications){
		LOGGER.debug("Saving all user Notifications ={}",notifications);
		userNotificationRepository.save(notifications);
	}

	@Override
	public Optional<UserNotification> getByAuthTokenAndNotificationId(String authToken, long notificationId)
			throws InvalidAuthTokenException, InvalidUserException {
		Optional<User> user = userService.getValidUserForAuthToken(authToken);
		if(user.isPresent())
			return userNotificationRepository.getByUserIdAndNotificationId(user.get().getId(), notificationId);
		else 
			throw new InvalidUserException("Invalid Auth Token");
	}
}
