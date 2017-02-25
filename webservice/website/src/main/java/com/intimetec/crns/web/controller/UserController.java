package com.intimetec.crns.web.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.exceptions.InvalidUserException;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserRole;
import com.intimetec.crns.core.service.user.UserService;
import com.intimetec.crns.util.ResponseMessage;

@RequestMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE )
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;  
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
    public Map<String, Object> createAdmin(@RequestBody User user) {
    	user.setUserRole(UserRole.ADMIN);
        LOGGER.debug("Processing Admin user creation");
        try {
            userService.create(user);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
        }
        return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public Map<String, Object> createUser(@RequestBody User user) {
    	user.setUserRole(UserRole.USER);
        LOGGER.debug("Processing user creation");
        try {
            userService.create(user);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
        }
        return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
    }
    
    @RequestMapping(value = "/isUniqueUsername", method = RequestMethod.GET)
    public Map<String, Object> isUniqueUsername(@RequestParam String userName) {
        LOGGER.debug("Processing request for unique user name verification");
        try {
            if(userService.getUserByUserName(userName).isPresent()){
            	return ResponseMessage.failureResponse(HttpServletResponse.SC_CONFLICT, "User Name already exists");
            }
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate username", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
        }
        return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
    }
    
    @RequestMapping(value = "/isEmailRegistered", method = RequestMethod.GET)
    public Map<String, Object> isEmailRegistered(@RequestParam String email) {
        LOGGER.debug("Processing user creation");
        try {
            if(userService.getUserByEmail(email).isPresent()){
            	return ResponseMessage.failureResponse(HttpServletResponse.SC_CONFLICT, "Email already registered");
            }
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
        }
        return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/{id}",  method = RequestMethod.GET)
    public Map<String, Object> getProfileByID(@PathVariable Long id) {
        LOGGER.debug("Getting user page for user={}", id);
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()) {
        	Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
        	response.put("data", userService.removeSensitiveInfo(user.get()));
        	return response;
        } else {
        	return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, String.format("User=%s not found", id));
        }
    }
    
    //Get Profile of User based on Auth Token
    @RequestMapping(value = "/getProfile",  method = RequestMethod.GET)
    public Map<String, Object> getProfile(HttpServletRequest request) {
    	String authToken = request.getHeader("authToken");
        LOGGER.debug("Getting user page based on Auth Token:", authToken);
        Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if(user.isPresent()) {
	        	Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	        	response.put("data", userService.removeSensitiveInfo(user.get()));
	        	return response;
	        }
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Duplicate email address");
        }
		return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "User not found");
    }
    
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/{id}/setProfile",  method = RequestMethod.POST)
    public Map<String, Object> setProfileByID(@PathVariable Long id, @RequestBody User user) {
        LOGGER.debug("Updating user page for user={}", id);
        try {
        	user = userService.update(id, user);
        	Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
        	response.put("data", userService.removeSensitiveInfo(user));
        	return response;
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, String.format("User=%s not found", id));
		} catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Duplicate email address");
        }
    }
    
    //Get Profile of User based on Auth Token
    @RequestMapping(value = "/setProfile",  method = RequestMethod.POST)
    public Map<String, Object> setProfile(HttpServletRequest request, @RequestBody User user) {
    	String authToken = request.getHeader("authToken");
    	LOGGER.debug("Updating user page for user={} with authToken", authToken);
        try {
        	user = userService.update(authToken, user);
        	Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
        	response.put("data", userService.removeSensitiveInfo(user));
        	return response;
		} catch (InvalidUserException|InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, String.format("User=%s not found", authToken));
		} catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Duplicate email address");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Collection<User> getUserCreatePage() {
    	Collection<User> users = userService.getAllUsers();
    	return users;
    }
}
