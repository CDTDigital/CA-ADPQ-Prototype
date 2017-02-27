package com.intimetec.crns.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
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
import com.intimetec.crns.core.exceptions.InvalidLocationCoordinatesException;
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
	public Map<String, Object> createAdmin(HttpServletResponse response, @RequestBody RestUser restUser)  
				throws IOException, ServletException{
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
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public Map<String, Object> createUser(HttpServletResponse response, @RequestBody RestUser restUser) {
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
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	// Validate if the username supplied is already registered or not
	@RequestMapping(value = "/isUniqueUsername", method = RequestMethod.GET)
	public Map<String, Object> isUniqueUsername(HttpServletResponse response, @RequestParam String userName) {
		LOGGER.debug("Processing request for unique user name verification");
		try {
			if (userService.getUserByUserName(userName).isPresent()) {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_CONFLICT, "User Name already exists", response);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate username", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	// Validate if the email supplied is already registered or not
	@RequestMapping(value = "/isEmailRegistered", method = RequestMethod.GET)
	public Map<String, Object> isEmailRegistered(HttpServletResponse response, @RequestParam String email) {
		LOGGER.debug("Processing user creation");
		try {
			if (userService.getUserByEmail(email).isPresent()) {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_CONFLICT, "Email already registered", response);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	// Get Profile of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Map<String, Object> getProfileByID(HttpServletResponse response, @PathVariable Long id) {
		LOGGER.debug("Getting user page for user={}", id);
		Optional<User> user = userService.getUserById(id);
		if (user.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			RestUser restUser = RestObjectToModelObjectMapper.UserToRestUser(user.get());
			Optional<UserLocation> location = userLocationService.getProfileLocationByUserId(user.get().getId());
			if (location.isPresent()) {
				RestLocation restLocation = RestObjectToModelObjectMapper.UserLocationToRestLocation(location.get());
				restUser.setLocation(restLocation);
			}
			restUser.setPassword(null);
			responseMessage.put("data", restUser);
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", id), response);
		}
	}

	// Get Profile of User based on Auth Token
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public Map<String, Object> getProfile(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user page based on Auth Token:", authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				RestUser restUser = RestObjectToModelObjectMapper.UserToRestUser(user.get());
				Optional<UserLocation> location = userLocationService.getProfileLocationByUserId(user.get().getId());
				if (location.isPresent()) {
					RestLocation restLocation = RestObjectToModelObjectMapper
							.UserLocationToRestLocation(location.get());
					restUser.setLocation(restLocation);
				}
				restUser.setPassword(null);
				responseMessage.put("data", restUser);
				return responseMessage;
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), response);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Duplicate email address", response);
		}
		return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "User not found", response);
	}

	// Set Profile of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public Map<String, Object> setProfileByID(@PathVariable Long id, @RequestBody RestUser restUser, HttpServletResponse response) {
		LOGGER.debug("Updating user page for user={}", id);
		try {
			User user = userService.update(id, RestObjectToModelObjectMapper.RestUserToUser(restUser));
			RestLocation location = restUser.getLocation();
			UserLocation userLocation = RestObjectToModelObjectMapper.RestLocationToUserLocation(location);
			userLocation.setUserId(user.getId());
			userLocation = userLocationService.save(userLocation);
			Map<String, Object> responseMap = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			userService.removeSensitiveInfo(user);
			restUser = RestObjectToModelObjectMapper.UserToRestUser(user);
			restUser.setLocation(RestObjectToModelObjectMapper.UserLocationToRestLocation(userLocation));
			responseMap.put("data", RestObjectToModelObjectMapper.UserToRestUser(user));
			return responseMap;
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", id), response);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), response);
		}
	}

	// Set Profile of User based on Auth Token
	@RequestMapping(value = "/setProfile", method = RequestMethod.POST)
	public Map<String, Object> setProfile(HttpServletRequest request, HttpServletResponse response, @RequestBody RestUser restUser) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Updating user page for user={} with authToken", authToken);
		try {
			User user = userService.update(authToken, RestObjectToModelObjectMapper.RestUserToUser(restUser));
			RestLocation location = restUser.getLocation();
			UserLocation userLocation = RestObjectToModelObjectMapper.RestLocationToUserLocation(location);
			userLocation.setUserId(user.getId());
			userLocation = userLocationService.save(userLocation);
			restUser.setLocation(RestObjectToModelObjectMapper.UserLocationToRestLocation(userLocation));
			Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			userService.removeSensitiveInfo(user);
			responseMessage.put("data", restUser);
			return responseMessage;
		} catch (InvalidUserException | InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", authToken), response);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), response);
		}
	}

	// Get Notification Options of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/getNotificationOptions", method = RequestMethod.GET)
	public Map<String, Object> getNotificationOptionsByID(HttpServletResponse response, @PathVariable Long id) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserNotificationOptions> options = userNotificationOptionsService.getByUserId(id);
		if (options.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", options.get());
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	// Get Notification Options of User based on Auth Token
	@RequestMapping(value = "/getNotificationOptions", method = RequestMethod.GET)
	public Map<String, Object> getNotificationOptions(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", authToken);
		UserNotificationOptions options;
		try {
			options = userNotificationOptionsService.getByAuthToken(authToken);
			if (options != null) {
				Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				responseMessage.put("data", options);
				return responseMessage;
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found", response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	// Get Notification Options of User based on User Id
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/setNotificationOptions", method = RequestMethod.POST)
	public Map<String, Object> setNotificationOptionsByID(@PathVariable Long id,
			@RequestBody UserNotificationOptions options, HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		try {
			options = userNotificationOptionsService.saveNotificationOptionsByUserId(id, options);
			if (options != null) {
				Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				responseMessage.put("data", options);
				return responseMessage;
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found", response);
			}
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	// Get Notification Options of User based on Auth Token
	@RequestMapping(value = "/setNotificationOptions", method = RequestMethod.POST)
	public Map<String, Object> setNotificationOptions(HttpServletRequest request,
			@RequestBody UserNotificationOptions options, HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", authToken);
		try {
			options = userNotificationOptionsService.saveNotificationOptionsByAuthToken(authToken, options);
			if (options != null) {
				Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				responseMessage.put("data", options);
				return responseMessage;
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found", response);
			}
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	// Get list of all Users
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Collection<RestUser> getUserCreatePage() {
		Collection<RestUser> users = new ArrayList<RestUser>();
		for (User user : userService.getAllUsers()) {
			Optional<UserLocation> userLocation = userLocationService.getProfileLocationByUserId(user.getId());
			RestUser restUser = RestObjectToModelObjectMapper.UserToRestUser(user);
			if (userLocation.isPresent())
				restUser.setLocation(RestObjectToModelObjectMapper.UserLocationToRestLocation(userLocation.get()));
			restUser.setPassword(null);
			users.add(restUser);
		}
		return users;
	}

	// Get Profile location of User
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/getProfileLocation", method = RequestMethod.GET)
	public Map<String, Object> getProfileLocationByID(@PathVariable Long id, HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserLocation> location = userLocationService.getProfileLocationByUserId(id);
		if (location.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", location.get());
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	// Get user profile location of User based on Auth Token
	@RequestMapping(value = "/getProfileLocation", method = RequestMethod.GET)
	public Map<String, Object> getProfileLocation(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Optional<UserLocation> location = userLocationService.getProfileLocationByUserId(user.get().getId());
				if (location.isPresent()) {
					Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
					responseMessage.put("data", location.get());
					return responseMessage;
				} else {
					return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
							"User Location data not found", response);
				}
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Location data not found", response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	// Get Current location of User based on User ID
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/getCurrentLocation", method = RequestMethod.GET)
	public Map<String, Object> getCurrentLocationByID(@PathVariable Long id, HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserLocation> location = userLocationService.getCurrentLocationByUserId(id);
		if (location.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", location.get());
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	// Get current location of User based on Auth Token
	@RequestMapping(value = "/getCurrentLocation", method = RequestMethod.GET)
	public Map<String, Object> getCurrentLocation(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Optional<UserLocation> location = userLocationService.getCurrentLocationByUserId(user.get().getId());
				if (location.isPresent()) {
					Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
					responseMessage.put("data", location.get());
					return responseMessage;
				} else {
					return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
							"User Location data not found", response);
				}
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Location data not found", response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	// Set Current location of User based on User ID
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/setCurrentLocation", method = RequestMethod.POST)
	public Map<String, Object> setCurrentLocationByID(@PathVariable Long id, @RequestParam("lat") String latitude,
			@RequestParam("lng") String longitude, HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserLocation> optionalLocation = userLocationService.getCurrentLocationByUserId(id);
		UserLocation location = null;
		if (optionalLocation.isPresent()) {
			location = optionalLocation.get();
		} else {
			location = new UserLocation();
		}
		location.setCurrentLocation(true);
		location.setUserId(id);
		try {
			location = userLocationService.saveLocation(location, latitude, longitude);
			Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", location);
			return responseMessage;
		} catch (InvalidLocationCoordinatesException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					e.getMessage(), response);
		}
		
	}

	// Set current location of User based on Auth Token
	@RequestMapping(value = "/setCurrentLocation", method = RequestMethod.POST)
	public Map<String, Object> setCurrentLocation(HttpServletRequest request, HttpServletResponse response, @RequestParam("lat") String latitude,
			@RequestParam("lng") String longitude) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Optional<UserLocation> optionalLocation = userLocationService
						.getCurrentLocationByUserId(user.get().getId());
				UserLocation location = null;
				if (optionalLocation.isPresent()) {
					location = optionalLocation.get();
				} else {
					location = new UserLocation();
				}
				location.setCurrentLocation(true);
				location.setUserId(user.get().getId());
				try {
					location = userLocationService.saveLocation(location, latitude, longitude);
					Map<String, Object> responseMessage = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
					responseMessage.put("data", location);
					return responseMessage;
				} catch (InvalidLocationCoordinatesException e) {
					return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
							e.getMessage(), response);
				}
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
						"User Location data not found", response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "User Location data not found", response);
		}
	}
}
