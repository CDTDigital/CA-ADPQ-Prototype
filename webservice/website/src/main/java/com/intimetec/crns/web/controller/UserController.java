package com.intimetec.crns.web.controller;

import java.util.ArrayList;
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
import com.intimetec.crns.core.models.UserLocation;
import com.intimetec.crns.core.models.UserNotificationOptions;
import com.intimetec.crns.core.models.UserRole;
import com.intimetec.crns.core.restmodels.RestLocation;
import com.intimetec.crns.core.restmodels.RestUser;
import com.intimetec.crns.core.service.user.UserService;
import com.intimetec.crns.core.service.userLocation.UserLocationService;
import com.intimetec.crns.core.service.usernotification.UserNotificationOptionsService;
import com.intimetec.crns.util.ResponseMessage;
import com.intimetec.crns.util.RestObjectToModelObjectMapper;

@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserLocationService userLocationService;
	@Autowired
	private UserNotificationOptionsService userNotificationOptionsService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
	public Map<String, Object> createAdmin(@RequestBody RestUser restUser) {
		restUser.setUserRole(UserRole.ADMIN);
		LOGGER.debug("Processing Admin user creation");
		try {
			restUser.setUserNotificationOptions(null);
			User user = RestObjectToModelObjectMapper.RestUserToUser(restUser);
			user.setEnabled(true);
			user = userService.create(user);
			if (restUser.getLocation() != null) {
				UserLocation userLocation = RestObjectToModelObjectMapper
						.RestLocationToUserLocation(restUser.getLocation());
				userLocation.setUserId(user.getId());
				userLocationService.save(userLocation);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public Map<String, Object> createUser(@RequestBody RestUser restUser) {
		restUser.setUserRole(UserRole.USER);
		System.out.println("user: " + restUser);
		LOGGER.debug("Processing user creation\n" + restUser);
		try {
			restUser.setUserNotificationOptions(null);
			User user = RestObjectToModelObjectMapper.RestUserToUser(restUser);
			user.setEnabled(true);
			user = userService.create(user);
			if (restUser.getLocation() != null) {
				UserLocation userLocation = RestObjectToModelObjectMapper
						.RestLocationToUserLocation(restUser.getLocation());
				userLocation.setUserId(user.getId());
				userLocationService.save(userLocation);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	// Validate if the username supplied is already registered or not
	@RequestMapping(value = "/isUniqueUsername", method = RequestMethod.GET)
	public Map<String, Object> isUniqueUsername(@RequestParam String userName) {
		LOGGER.debug("Processing request for unique user name verification");
		try {
			if (userService.getUserByUserName(userName).isPresent()) {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_CONFLICT, "User Name already exists");
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate username", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	// Validate if the email supplied is already registered or not
	@RequestMapping(value = "/isEmailRegistered", method = RequestMethod.GET)
	public Map<String, Object> isEmailRegistered(@RequestParam String email) {
		LOGGER.debug("Processing user creation");
		try {
			if (userService.getUserByEmail(email).isPresent()) {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_CONFLICT, "Email already registered");
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	// Get Profile of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Map<String, Object> getProfileByID(@PathVariable Long id) {
		LOGGER.debug("Getting user page for user={}", id);
		Optional<User> user = userService.getUserById(id);
		if (user.isPresent()) {
			Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			RestUser restUser = RestObjectToModelObjectMapper.UserToRestUser(user.get());
			RestLocation restLocation = RestObjectToModelObjectMapper
					.UserLocationToRestLocation(userLocationService.getProfileLocationByUserId(id));
			restUser.setLocation(restLocation);
			restUser.setPassword(null);
			response.put("data", restUser);
			return response;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", id));
		}
	}

	// Get Profile of User based on Auth Token
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public Map<String, Object> getProfile(HttpServletRequest request) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user page based on Auth Token:", authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				RestUser restUser = RestObjectToModelObjectMapper.UserToRestUser(user.get());
				RestLocation restLocation = RestObjectToModelObjectMapper.UserLocationToRestLocation(
						userLocationService.getProfileLocationByUserId(user.get().getId()));
				restUser.setLocation(restLocation);
				restUser.setPassword(null);
				response.put("data", restUser);
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

	// Set Profile of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public Map<String, Object> setProfileByID(@PathVariable Long id, @RequestBody RestUser restUser) {
		LOGGER.debug("Updating user page for user={}", id);
		try {
			User user = userService.update(id, RestObjectToModelObjectMapper.RestUserToUser(restUser));
			RestLocation location = restUser.getLocation();
			UserLocation userLocation = RestObjectToModelObjectMapper.RestLocationToUserLocation(location);
			userLocation.setUserId(user.getId());
			userLocation = userLocationService.save(userLocation);
			restUser.setLocation(RestObjectToModelObjectMapper.UserLocationToRestLocation(userLocation));
			Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			userService.removeSensitiveInfo(user);
			response.put("data", RestObjectToModelObjectMapper.UserToRestUser(user));
			return response;
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", id));
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
	}

	// Set Profile of User based on Auth Token
	@RequestMapping(value = "/setProfile", method = RequestMethod.POST)
	public Map<String, Object> setProfile(HttpServletRequest request, @RequestBody RestUser restUser) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Updating user page for user={} with authToken", authToken);
		try {
			User user = userService.update(authToken, RestObjectToModelObjectMapper.RestUserToUser(restUser));
			RestLocation location = restUser.getLocation();
			UserLocation userLocation = RestObjectToModelObjectMapper.RestLocationToUserLocation(location);
			userLocation.setUserId(user.getId());
			userLocation = userLocationService.save(userLocation);
			restUser.setLocation(RestObjectToModelObjectMapper.UserLocationToRestLocation(userLocation));
			Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			userService.removeSensitiveInfo(user);
			response.put("data", restUser);
			return response;
		} catch (InvalidUserException | InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", authToken));
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
	}

	// Get Notification Options of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/getNotificationOptions", method = RequestMethod.GET)
	public Map<String, Object> getNotificationOptionsByID(@PathVariable Long id) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserNotificationOptions> options = userNotificationOptionsService.getByUserId(id);
		if (options.isPresent()) {
			Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			response.put("data", options.get());
			return response;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found");
		}
	}

	// Get Notification Options of User based on Auth Token
	@RequestMapping(value = "/getNotificationOptions", method = RequestMethod.GET)
	public Map<String, Object> getNotificationOptions(HttpServletRequest request) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", authToken);
		UserNotificationOptions options;
		try {
			options = userNotificationOptionsService.getByAuthToken(authToken);
			if (options != null) {
				Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				response.put("data", options);
				return response;
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found");
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found");
		}
	}

	// Get Notification Options of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/setNotificationOptions", method = RequestMethod.POST)
	public Map<String, Object> setNotificationOptionsByID(@PathVariable Long id,
			@RequestBody UserNotificationOptions options) {
		LOGGER.debug("Getting user notification options for user={}", id);
		try {
			options = userNotificationOptionsService.saveNotificationOptionsByUserId(id, options);
			if (options != null) {
				Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				response.put("data", options);
				return response;
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found");
			}
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found");
		}
	}

	// Get Notification Options of User based on Auth Token
	@RequestMapping(value = "/setNotificationOptions", method = RequestMethod.POST)
	public Map<String, Object> setNotificationOptions(HttpServletRequest request,
			@RequestBody UserNotificationOptions options) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", authToken);
		try {
			options = userNotificationOptionsService.saveNotificationOptionsByAuthToken(authToken, options);
			if (options != null) {
				Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				response.put("data", options);
				return response;
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found");
			}
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found");
		}
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Collection<RestUser> getUserCreatePage() {
		Collection<RestUser> users = new ArrayList<RestUser>();
		for(User user: userService.getAllUsers()){
			userService.removeSensitiveInfo(user);
			users.add(RestObjectToModelObjectMapper.UserToRestUser(user));
		}
		return users;
	}
}
