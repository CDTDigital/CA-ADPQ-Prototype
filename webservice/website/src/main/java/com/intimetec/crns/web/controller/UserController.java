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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

/**
 * 
 * @author In Time Tec
 */
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {

	/**
	 * To log the application messages. 
	 */
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(UserController.class);
	
	/**
	 * Instance of the class {@link UserService}. 
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Instance of the class {@link UserLocationService}. 
	 */
	@Autowired
	private UserLocationService userLocationService;
	
	/**
	 * Instance of the class {@link UserNotificationOptionsService}. 
	 */
	@Autowired
	private UserNotificationOptionsService userNotificationOptionsService;

	/**
	 * Create the Admin. 
	 * @param response           the HTTP servlet response.
	 * @param restUser           the rest User.
	 * @throws IOException       if any input or output exception occurred.
	 * @throws ServletException  if any problem of servlet occurred.
	 * @return                   the admin created.
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
	public final Map<String, Object> createAdmin(
			final HttpServletResponse response, 
			@RequestBody final RestUser restUser)  
				throws IOException, ServletException {
		restUser.setUserRole(UserRole.ADMIN);
		LOGGER.debug("Processing Admin user creation");
		try {
			restUser.setUserNotificationOptions(null);
			User user = RestObjectToModelObjectMapper.restUserToUser(restUser);
			user.setEnabled(true);
			user = userService.create(user);
			if (restUser.getLocation() != null) {
				UserLocation userLocation = RestObjectToModelObjectMapper
						.RestLocationToUserLocation(restUser.getLocation());
				userLocation.setUserId(user.getId());
				userLocationService.save(userLocation);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, "
					+ "assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	/**
	 * Create the User. 
	 * @param response   the HTTP servlet response.
	 * @param restUser   the rest User.
	 * @return           the User created.
	 */
	@RequestMapping(value = "/createUser",
			method = RequestMethod.POST)
	public final Map<String, Object> createUser(
			final HttpServletResponse response, 
			@RequestBody final RestUser restUser) {
		restUser.setUserRole(UserRole.USER);
		LOGGER.debug("Processing user creation\n" + restUser);
		try {
			restUser.setUserNotificationOptions(null);
			User user = RestObjectToModelObjectMapper.restUserToUser(restUser);
			user.setEnabled(true);
			user = userService.create(user);
			if (restUser.getLocation() != null) {
				UserLocation userLocation = RestObjectToModelObjectMapper
						.RestLocationToUserLocation(restUser.getLocation());
				userLocation.setUserId(user.getId());
				userLocationService.save(userLocation);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user,"
					+ " assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	/**
	 * Validate if the user name supplied is already registered or not.
	 * @param response           the HTTP servlet response.
	 * @param userName           the user name of the User.
	 * @return                   the response.
	 */
	@RequestMapping(value = "/isUniqueUsername", method = RequestMethod.GET)
	public final Map<String, Object> isUniqueUsername(
			final HttpServletResponse response, 
			@RequestParam final String userName) {
		LOGGER.debug("Processing request for unique user name verification");
		try {
			if (userService.getUserByUserName(userName).isPresent()) {
				return ResponseMessage.failureResponse(
						HttpServletResponse.SC_CONFLICT, 
						"User Name already exists", response);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, "
					+ "assuming duplicate username", e);
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST, 
					"Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	/**
	 * Validate if the email supplied is already registered or not.
	 * @param response    the HTTP servlet response.
	 * @param email       the email of the User.
	 * @return            the response.
	 */
	@RequestMapping(value = "/isEmailRegistered", method = RequestMethod.GET)
	public final Map<String, Object> isEmailRegistered(
			final HttpServletResponse response,
			@RequestParam final String email) {
		LOGGER.debug("Processing user creation");
		try {
			if (userService.getUserByEmail(email).isPresent()) {
				return ResponseMessage.failureResponse(HttpServletResponse.
						SC_CONFLICT, "Email already registered", response);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user,"
					+ " assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "Not valid inputs", response);
		}
		return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
	}

	/**
	 * Get Profile of the User based on User Id.
	 * @param response    the HTTP servlet response.
	 * @param id          the id of the User.
	 * @return            the response.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public final Map<String, Object> getProfileByID(
			final HttpServletResponse response, 
			@PathVariable final Long id) {
		LOGGER.debug("Getting user page for user={}", id);
		Optional<User> user = userService.getUserById(id);
		if (user.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			RestUser restUser = RestObjectToModelObjectMapper.
					UserToRestUser(user.get());
			Optional<UserLocation> location = userLocationService.
					getProfileLocationByUserId(user.get().getId());
			if (location.isPresent()) {
				RestLocation restLocation = RestObjectToModelObjectMapper.
						UserLocationToRestLocation(location.get());
				restUser.setLocation(restLocation);
			}
			restUser.setPassword(null);
			responseMessage.put("data", restUser);
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", id), response);
		}
	}

	/**
	 * Get Profile of User based on Auth Token.
	 * @param response    the HTTP servlet response.
	 * @param request     the HTTP servlet request.
	 * @return            the response.
	 */
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public final Map<String, Object> getProfile(
			final HttpServletRequest request, 
			final HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user page based on Auth Token:", authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Map<String, Object> responseMessage = ResponseMessage.
						successResponse(HttpServletResponse.SC_OK);
				RestUser restUser = RestObjectToModelObjectMapper.
						UserToRestUser(user.get());
				Optional<UserLocation> location = userLocationService.
						getProfileLocationByUserId(user.get().getId());
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
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST, e.getMessage(),
					response);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user, "
					+ "assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "Duplicate email address", response);
		}
		return ResponseMessage.failureResponse(HttpServletResponse.
				SC_BAD_REQUEST, "User not found", response);
	}

	/**
	 * Set Profile of User based on User Id.
	 * @param id          the id of the User.
	 * @param restUser    the rest User.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public final Map<String, Object> setProfileByID(@PathVariable final Long id,
			@RequestBody RestUser restUser, 
			final HttpServletResponse response) {
		LOGGER.debug("Updating user page for user={}", id);
		try {
			User user = userService.update(id, RestObjectToModelObjectMapper.
					restUserToUser(restUser));
			RestLocation location = restUser.getLocation();
			UserLocation userLocation = RestObjectToModelObjectMapper.
					RestLocationToUserLocation(location);
			userLocation.setUserId(user.getId());
			Optional<UserLocation> profileLocation = userLocationService.
					getProfileLocationByUserId(user.getId());
			if (profileLocation.isPresent()) {
				userLocation.setId(profileLocation.get().getId());
			}
			userLocation = userLocationService.save(userLocation);
			Map<String, Object> responseMap = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			userService.removeSensitiveInfo(user);
			restUser = RestObjectToModelObjectMapper.UserToRestUser(user);
			restUser.setLocation(RestObjectToModelObjectMapper.
					UserLocationToRestLocation(userLocation));
			responseMap.put("data", restUser);
			return responseMap;
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", id), response);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, e.getMessage(), response);
		}
	}

	/**
	 * Set Profile of User based on Auth Token.
	 * @param request     the HTTP servlet request.
	 * @param response    the HTTP servlet response.
	 * @param restUser    the rest User.
	 * @return            the response.
	 */
	@RequestMapping(value = "/setProfile", method = RequestMethod.POST)
	public final Map<String, Object> setProfile(
			final HttpServletRequest request,
			final HttpServletResponse response, 
			@RequestBody RestUser restUser) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Updating user page for user={} with authToken",
				authToken);
		try {
			User user = userService.update(authToken, 
					RestObjectToModelObjectMapper.restUserToUser(restUser));
			RestLocation location = restUser.getLocation();
			UserLocation userLocation = RestObjectToModelObjectMapper.
					RestLocationToUserLocation(location);
			userLocation.setUserId(user.getId());
			Optional<UserLocation> profileLocation = userLocationService.getProfileLocationByUserId(user.getId());
			if(profileLocation.isPresent()){
				userLocation.setId(profileLocation.get().getId());
			}
			userLocation = userLocationService.save(userLocation);
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			userService.removeSensitiveInfo(user);
			restUser = RestObjectToModelObjectMapper.UserToRestUser(user);
			restUser.setLocation(RestObjectToModelObjectMapper.UserLocationToRestLocation(userLocation));
			responseMessage.put("data", restUser);
			return responseMessage;
		} catch (InvalidUserException | InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					String.format("User=%s not found", authToken), response);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the user", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, e.getMessage(), response);
		}
	}

	/**
	 * Get Notification Options of User based on User Id.
	 * @param id          the id of the User.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/getNotificationOptions", 
	method = RequestMethod.GET)
	public final Map<String, Object> getNotificationOptionsByID(
			final HttpServletResponse response, @PathVariable final Long id) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserNotificationOptions> options = 
				userNotificationOptionsService.getByUserId(id);
		if (options.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", options.get());
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	/**
	 * Get Notification Options of the User based on Auth Token.
	 * @param request     the HTTP servlet request.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@RequestMapping(value = "/getNotificationOptions", method = 
			RequestMethod.GET)
	public final Map<String, Object> getNotificationOptions(
			final HttpServletRequest request, 
			final HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", 
				authToken);
		UserNotificationOptions options;
		try {
			options = userNotificationOptionsService.getByAuthToken(authToken);
			if (options != null) {
				Map<String, Object> responseMessage = ResponseMessage.
						successResponse(HttpServletResponse.SC_OK);
				responseMessage.put("data", options);
				return responseMessage;
			} else {
				return ResponseMessage.failureResponse(
						HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found", response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	/**
	 * Set Notification Options of the User based on User Id.
	 * @param id          the id of the User.
	 * @param options     the notification options.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/setNotificationOptions", 
	method = RequestMethod.POST)
	public final Map<String, Object> setNotificationOptionsByID(
			@PathVariable final Long id,
			@RequestBody UserNotificationOptions options, 
			final HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		try {
			options = userNotificationOptionsService.
					saveNotificationOptionsByUserId(id, options);
			if (options != null) {
				Map<String, Object> responseMessage = ResponseMessage.
						successResponse(HttpServletResponse.SC_OK);
				responseMessage.put("data", options);
				return responseMessage;
			} else {
				return ResponseMessage.failureResponse(
						HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found", response);
			}
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	/**
	 * Set Notification Options of User based on Auth Token.
	 * @param request     the HTTP servlet request.
	 * @param options     the notification options.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@RequestMapping(value = "/setNotificationOptions", method = 
			RequestMethod.POST)
	public final Map<String, Object> setNotificationOptions(
			final HttpServletRequest request,
			@RequestBody UserNotificationOptions options, 
			final HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", 
				authToken);
		try {
			options = userNotificationOptionsService.
					saveNotificationOptionsByAuthToken(authToken, options);
			if (options != null) {
				Map<String, Object> responseMessage = ResponseMessage.
						successResponse(HttpServletResponse.SC_OK);
				responseMessage.put("data", options);
				return responseMessage;
			} else {
				return ResponseMessage.failureResponse(
						HttpServletResponse.SC_BAD_REQUEST,
						"User Notification data not found", response);
			}
		} catch (InvalidUserException e) {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					"User Notification data not found", response);
		}
	}

	/**
	 * Get list of all the Users.
	 * @return    the users.
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public final Collection<RestUser> getUserCreatePage() {
		Collection<RestUser> users = new ArrayList<RestUser>();
		for (User user : userService.getAllUsers()) {
			Optional<UserLocation> userLocation = userLocationService.
					getProfileLocationByUserId(user.getId());
			RestUser restUser = RestObjectToModelObjectMapper.
					UserToRestUser(user);
			if (userLocation.isPresent()) {
				restUser.setLocation(RestObjectToModelObjectMapper.
						UserLocationToRestLocation(userLocation.get()));
			}
			restUser.setPassword(null);
			users.add(restUser);
		}
		return users;
	}

	/**
	 * Get Profile location of the User.
	 * @param id          the id of the User.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/getProfileLocation",
	method = RequestMethod.GET)
	public final Map<String, Object> getProfileLocationByID(
			@PathVariable final Long id, 
			final HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserLocation> location = userLocationService.
				getProfileLocationByUserId(id);
		if (location.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", location.get());
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	/**
	 * Get user profile location of User based on Auth Token.
	 * @param request     the HTTP servlet request.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@RequestMapping(value = "/getProfileLocation", method = RequestMethod.GET)
	public final Map<String, Object> getProfileLocation(
			final HttpServletRequest request, 
			final HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", 
				authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Optional<UserLocation> location = userLocationService.
						getProfileLocationByUserId(user.get().getId());
				if (location.isPresent()) {
					Map<String, Object> responseMessage = ResponseMessage.
							successResponse(HttpServletResponse.SC_OK);
					responseMessage.put("data", location.get());
					return responseMessage;
				} else {
					return ResponseMessage.failureResponse(
							HttpServletResponse.SC_BAD_REQUEST,
							"User Location data not found", response);
				}
			} else {
				return ResponseMessage.failureResponse(
						HttpServletResponse.SC_BAD_REQUEST,
						"User Location data not found", response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	/**
	 * Get Current location of User based on User ID.
	 * @param id          the id of the User.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/getCurrentLocation", 
	method = RequestMethod.GET)
	public final Map<String, Object> getCurrentLocationByID(
			@PathVariable final Long id, final HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserLocation> location = userLocationService.
				getCurrentLocationByUserId(id);
		if (location.isPresent()) {
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", location.get());
			return responseMessage;
		} else {
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	/**
	 * Get current location of User based on Auth Token.
	 * @param request     the HTTP servlet request.     
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@RequestMapping(value = "/getCurrentLocation", 
			method = RequestMethod.GET)
	public final Map<String, Object> getCurrentLocation(
			final HttpServletRequest request, 
			final HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}",
				authToken);
		Optional<User> user;
		try {
			user = userService.getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Optional<UserLocation> location = userLocationService.
						getCurrentLocationByUserId(user.get().getId());
				if (location.isPresent()) {
					Map<String, Object> responseMessage = ResponseMessage.
							successResponse(HttpServletResponse.SC_OK);
					responseMessage.put("data", location.get());
					return responseMessage;
				} else {
					return ResponseMessage.failureResponse(
							HttpServletResponse.SC_BAD_REQUEST,
							"User Location data not found", response);
				}
			} else {
				return ResponseMessage.failureResponse(HttpServletResponse.
						SC_BAD_REQUEST, "User Location data not found", 
						response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "User Location data not found", response);
		}
	}

	/**
	 * Set Current location of the User based on User ID.
	 * @param id          the Id of the User.
	 * @param latitude    the latitude of the location.
	 * @param longitude   the longitude of the location.
	 * @param response    the HTTP servlet response.
	 * @return            the response.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/{id}/setCurrentLocation",
	method = RequestMethod.POST)
	public final Map<String, Object> setCurrentLocationByID(
			@PathVariable final Long id, 
			@RequestParam("lat") final String latitude,
			@RequestParam("lng") final String longitude, 
			final HttpServletResponse response) {
		LOGGER.debug("Getting user notification options for user={}", id);
		Optional<UserLocation> optionalLocation = userLocationService.
				getCurrentLocationByUserId(id);
		UserLocation location = null;
		if (optionalLocation.isPresent()) {
			location = optionalLocation.get();
		} else {
			location = new UserLocation();
		}
		location.setCurrentLocation(true);
		location.setUserId(id);
		try {
			location = userLocationService.saveLocation(
					location, latitude, longitude);
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", location);
			return responseMessage;
		} catch (InvalidLocationCoordinatesException e) {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					e.getMessage(), response);
		}
		
	}

	/**
	 *  Set current location of the User based on Auth Token.
	 * @param request     the HTTP servlet request.   
	 * @param response    the HTTP servlet response.
	 * @param latitude    the latitude of the location.
	 * @param longitude   the longitude of the location.	
	 * @return            the response.
	 */
	@RequestMapping(value = "/setCurrentLocation", method = RequestMethod.POST)
	public final Map<String, Object> setCurrentLocation(
			final HttpServletRequest request, 
			final HttpServletResponse response,
			@RequestParam("lat") final String latitude,
			@RequestParam("lng") final String longitude) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting user notification options for user={}", 
				authToken);
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
					location = userLocationService.saveLocation(location, 
							latitude, longitude);
					Map<String, Object> responseMessage = ResponseMessage.
							successResponse(HttpServletResponse.SC_OK);
					responseMessage.put("data", location);
					return responseMessage;
				} catch (InvalidLocationCoordinatesException e) {
					return ResponseMessage.failureResponse(
							HttpServletResponse.SC_BAD_REQUEST,
							e.getMessage(), response);
				}
			} else {
				return ResponseMessage.failureResponse(
						HttpServletResponse.SC_BAD_REQUEST,
						"User Location data not found", response);
			}
		} catch (InvalidAuthTokenException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "User Location data not found", response);
		}
	}
	
	/**
	 * To authenticate the logged in User.
	 * @return the authentication.
	 */	
	@RequestMapping(value = "checkUserLogin", method = RequestMethod.GET)
	public final Authentication getLoggedInUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
