package com.intimetec.crns.core.service.user;

import java.util.Collection;
import java.util.Optional;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.exceptions.InvalidUserException;
import com.intimetec.crns.core.models.User;

/**
 * @author In Time Tec
 */
public interface UserService {
	Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);
    
    Optional<User> getUserByUserName(String userName);

    Collection<User> getAllUsers();

    User create(User user);
	
	Optional<User> getValidUserForAuthToken(String authToken) throws InvalidAuthTokenException;
	
	User update(long id, User user) throws InvalidUserException;
	
	User update(String authToken, User user) throws InvalidUserException, InvalidAuthTokenException;

	User removeSensitiveInfo(User user);
	
	Collection<User> getUsersByZipCode(String zipCode);
}
