package com.intimetec.crns.core.service.user;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.exceptions.InvalidUserException;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.repository.UserRepository;
import com.intimetec.crns.core.service.userdevice.UserDeviceService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserDeviceService userDeviceService;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.debug("Getting user={}", id);
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        Optional<User> user= userRepository.findOneByEmail(email);
       return user;
    }
    
    @Override
    public Optional<User> getUserByUserName(String userName) {
        System.out.println("Getting user by username={}"+ userName);
        LOGGER.debug("Getting user by username={}"+ userName);
        Optional<User> user= userRepository.findOneByUserName(userName);
       return user;
    }
    
    @Override
    public Collection<User> getAllUsers() {
        LOGGER.debug("Getting all users");
        return userRepository.findAll(new Sort("email"));
    }

    @Override
    public User create(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
    
	
	@Override
	public Optional<User> getValidUserForAuthToken(String authToken) throws InvalidAuthTokenException{
		Optional<UserDevice> userDevice =  userDeviceService.getByAuthToken(authToken);
		if(userDevice.isPresent()){
			return getUserById(userDevice.get().getUser().getId());
		} else {
			throw new InvalidAuthTokenException("Invalid Authentication Token Supplied");
		}
	}
	
	@Override
	public User update(long id, User user) throws InvalidUserException {
		Optional<User> userById = getUserById(id);
        if(userById.isPresent()) {
        	userById.get().setFirstName(user.getFirstName());
        	userById.get().setLastName(user.getLastName());
        	userById.get().setMobileNo(user.getMobileNo());
        	if(user.getPassword()!= null) {
        		userById.get().setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        	}
        	if(user.getEmail()!= null) {
        		userById.get().setEmail(user.getEmail());
        	}
        	if(user.getUserNotificationOptions()!= null) {
        		userById.get().setUserNotificationOptions(user.getUserNotificationOptions());
        	}
        	if(userById.get().getUserNotificationOptions()!= null) {
        		userById.get().getUserNotificationOptions().setUserId(userById.get().getId());
            	userById.get().setAccountSetupDone();
        	}
        	return userRepository.save(userById.get());
        } else {
        	throw new InvalidUserException("Supplied invalid userId to update.");
        }
	}

	@Override
	public User update(String authToken, User user) throws InvalidUserException, InvalidAuthTokenException {
		Optional<User> userById = getValidUserForAuthToken(authToken);
		System.out.println("user from setProfile: "+user);
		System.out.println("userById: "+userById);
        if(userById.isPresent()) {
        	userById.get().setFirstName(user.getFirstName());
        	userById.get().setLastName(user.getLastName());
        	userById.get().setMobileNo(user.getMobileNo());
        	if(user.getPassword()!= null) {
        		userById.get().setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        	}
        	if(user.getEmail()!= null) {
        		userById.get().setEmail(user.getEmail());
        	}
        	if(user.getUserNotificationOptions()!= null) {
        		userById.get().setUserNotificationOptions(user.getUserNotificationOptions());
        	}
        	if(userById.get().getUserNotificationOptions()!= null) {
        		userById.get().getUserNotificationOptions().setUserId(userById.get().getId());
            	userById.get().setAccountSetupDone();
        	}
        	return userRepository.save(userById.get());
        } else {
        	throw new InvalidUserException("Supplied invalid userId to update.");
        }
	}
	
	@Override
	public User removeSensitiveInfo(User user) {
		user.setPassword(null);
		return user;
	}

	@Override
	public Collection<User> getUsersByZipCode(String zipCode) {
		LOGGER.debug("Getting all belongs to same location based on ZipCode {}", zipCode);
        return userRepository.findUserByZipCode(zipCode);
	}
}
