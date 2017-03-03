package com.intimetec.crns.core.service.currentuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.CurrentUser;
import com.intimetec.crns.core.models.UserRole;

/**
 * @author In Time Tec
 */
@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	/**
	 * To log the application messages.
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(
    		CurrentUserDetailsService.class);

    @Override
	public final boolean canAccessUser(final CurrentUser currentUser, 
			final Long userId) {
        LOGGER.debug("Checking if user={} has access to user={}", 
        		currentUser, userId);
        return currentUser != null
                && (currentUser.getRole() == UserRole.ADMIN || (new Long(
                currentUser.getId())).equals(userId));
    }
    
    @Override
	public final boolean canAccessUserByRoles(final CurrentUser currentUser, 
			final String[] roles) {
        LOGGER.debug("Checking if user={} has access to user={}", 
        		currentUser, roles);
        if (currentUser != null) {
	        for (String role: roles) {
	        	if (currentUser.getRole().toString().equals(role)) {
	        		return true;
	        	}
	        }
    	}
        return false;
    }
}
