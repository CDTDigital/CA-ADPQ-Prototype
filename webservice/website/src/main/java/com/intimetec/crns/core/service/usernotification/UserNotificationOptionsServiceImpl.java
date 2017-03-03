package com.intimetec.crns.core.service.usernotification;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.exceptions.InvalidUserException;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserNotificationOptions;
import com.intimetec.crns.core.repository.UserNotificationOptionRepository;
import com.intimetec.crns.core.service.user.UserService;

/**
 * @author In Time Tec
 */
@Service
public class UserNotificationOptionsServiceImpl implements 
UserNotificationOptionsService {

	/**
	 * To log the application messages.
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(
    		UserNotificationOptionsServiceImpl.class);
    
    /**
     * Instance of the class {@link UserNotificationOptionRepository}.
     */
    @Autowired
    private UserNotificationOptionRepository userNotificationOptionRepository;
    
    /**
     * Instance of the class {@link UserService}.
     */
    @Autowired
    private UserService userService;

    @Override
    public Optional<UserNotificationOptions> getById(long id) {
        LOGGER.debug("Getting user Notification Options={}", id);
        return Optional.ofNullable(userNotificationOptionRepository.
        		findOne(id));
    }

    @Override
    public Optional<UserNotificationOptions> getByUserId(long userId) {
        LOGGER.debug("Getting user Notifications Options by userId={}", userId);
        Optional<UserNotificationOptions> userNotificationOptions = 
        		userNotificationOptionRepository.findOneByUserId(userId);
       return userNotificationOptions;
    }
    
    @Override
    public UserNotificationOptions getByAuthToken(String authToken) throws InvalidAuthTokenException {
    	Optional<User> user = userService.getValidUserForAuthToken(authToken);
    	if(user.isPresent()){
			return user.get().getUserNotificationOptions();
		} else {
			throw new InvalidAuthTokenException("Invalid User data");
		}
    }
    
    @Override
    public UserNotificationOptions save(UserNotificationOptions
    		userNotificationOptions) {
    	LOGGER.debug("Saving user Notification options = {}" 
    				+ userNotificationOptions);
       return userNotificationOptionRepository.save(userNotificationOptions);
    }

	@Override
	public UserNotificationOptions saveNotificationOptionsByUserId(long userId,
			UserNotificationOptions userNotificationOptions) throws InvalidUserException {
		if (userService.getUserById(userId).isPresent()){
			userNotificationOptions.setUserId(userId);
			return save(userNotificationOptions);
		} else {
			throw new InvalidUserException("Invalid user data");
		}
		
	}

	@Override
	public UserNotificationOptions saveNotificationOptionsByAuthToken(
			String authToken,
			UserNotificationOptions userNotificationOptions) throws 
			InvalidUserException {
		UserNotificationOptions options;
		try {
			options = getByAuthToken(authToken);
			if (options != null) {
				userNotificationOptions.setUserId(options.getUserId());
				return save(userNotificationOptions);
			} else {
				throw new InvalidUserException("Invalid user data");
			}
		} catch (InvalidAuthTokenException e) {
			throw new InvalidUserException("Invalid user data");
		}
	}
}
