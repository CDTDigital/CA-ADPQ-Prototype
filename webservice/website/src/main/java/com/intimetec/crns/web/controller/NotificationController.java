package com.intimetec.crns.web.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.intimetec.crns.core.exceptions.InvalidNotificatioException;
import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserNotification;
import com.intimetec.crns.core.restmodels.RestNotification;
import com.intimetec.crns.core.restmodels.RestUserNotification;
import com.intimetec.crns.core.service.notification.NotificationService;
import com.intimetec.crns.core.service.user.UserService;
import com.intimetec.crns.core.service.usernotification.UserNotificationService;
import com.intimetec.crns.util.ResponseMessage;
import com.intimetec.crns.util.RestObjectToModelObjectMapper;

/**
 * @author In Time Tec
 */
@RequestMapping(value = "/notifications", 
produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class NotificationController {

	/**
	 * To log the application messages. 
	 */
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(NotificationController.class);

	/**
	 * Instance of the class {@link NotificationService}. 
	 */
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * Instance of the class {@link UserService}. 
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Instance of the class {@link UserNotificationService}. 
	 */
	@Autowired
	private UserNotificationService userNotificationService;

	/**
	 * Saving a notification published by Admin, after saving the same
	 * notification will be sent to various users belong to the same
	 * zip-code/postal-code.
	 * 
	 * @param notification   the notification.
	 * @param response       the response.
	 * @return               the notification to be sent.
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public final Map<String, Object> sendNotification(
			@RequestBody Notification notification, 
			final HttpServletResponse response) {
		LOGGER.debug("Processing notification to save and send");
		try {
			notification.setSentBy(userService.getUserById(
					notification.getSentBy().getId()).get());
			notification = notificationService.save(notification);
			/**Sending mail notification to Users - This will be 
			 * a asynchronous call 
			 */
			notificationService.sendNotification(notification);
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", RestObjectToModelObjectMapper.
					NotificationToRestNotification(notification));
			return responseMessage;
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying "
					+ "to save the notification", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "Not valid inputs", response);
		}
	}

	/**
	 * Getting list of all notifications.
	 * @param response     the HTTP response.
	 * @return             the list of all notifications.
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public final Map<String, Object> getNotifications(
			final HttpServletResponse response) {
		LOGGER.debug("Getting all notifications {}");
		try {
			ArrayList<RestNotification> notifications = 
					new ArrayList<RestNotification>();
			for (Notification notification: notificationService.getAll()) {
				notification.getSentBy().setPassword(null);
				notifications.add(RestObjectToModelObjectMapper.
						NotificationToRestNotification(notification));
			}
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", notifications);
			return responseMessage;
		} catch (Exception e) {
			LOGGER.warn("Exception occurred when trying"
					+ " to load notifications", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST, "Unable to load notification list",
					response);
		}
	}

	/**
	 * Getting notification detail based on supplied ID.
	 * 
	 * @param id        the Id.
	 * @param response  the HTTP response.
	 * @return          the notification detail.
	 */
	@PreAuthorize("@currentUserServiceImpl."
			+ "canAccessUserByRoles(principal, {'ADMIN', 'USER'})")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public final Map<String, Object> getNotification(
			@PathVariable final Long id, final HttpServletResponse response) {
		LOGGER.debug("Getting notification based on ID {}", id);
		try {
			Optional<Notification> notification = notificationService.
					getById(id);
			if (notification.isPresent()) {
				Map<String, Object> responseMessage = ResponseMessage.
						successResponse(HttpServletResponse.SC_OK);
				notification.get().getSentBy().setPassword(null);
				responseMessage.put("data", RestObjectToModelObjectMapper.
						NotificationToRestNotification(notification.get()));
				return responseMessage;
			} else {
				throw new InvalidNotificatioException();
			}
		} catch (Exception e) {
			LOGGER.warn("Exception occurred when trying"
					+ " to load notification", e);
			return ResponseMessage.failureResponse(HttpServletResponse.
					SC_BAD_REQUEST,
					"Unable to load notification details", response);
		}
	}
	
	/**
	 * Getting notification list for specific User based on supplied ID.
	 * @param id        the Id.
	 * @param response  the HTTP response.
	 * @return          the notification list.
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUser("
			+ "principal, #id)")
	@RequestMapping(value = "/userNotifications/{id}", 
	method = RequestMethod.GET)
	public final Map<String, Object> getUserNotificationsByUserId(
			@PathVariable final Long id, final HttpServletResponse response) {
		LOGGER.debug("Getting notification based on ID {}", id);
		try {
			ArrayList<RestUserNotification> notifications = 
					new ArrayList<RestUserNotification>();
			for (UserNotification notification: userNotificationService.
					getUserNotificationsByUserId(id)) {
				notifications.add(RestObjectToModelObjectMapper.
						UserNotificationToRestUserNotification(notification));
			}
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", notifications);
			return responseMessage;
		} catch (Exception e) {
			LOGGER.warn("Exception occurred when trying"
					+ " to load notification", e);
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					"Unable to load notifications", response);
		}
	}
	
	/**
	 * Getting notification list for specific User based on supplied AuthToken.
	 * @param request   the HTTP request.
	 * @param response  the HTTP response.
	 * @return          the notification list.
	 */
	@RequestMapping(value = "/userNotifications", method = RequestMethod.GET)
	public final Map<String, Object> getUserNotifications(
			final HttpServletRequest request, 
			final HttpServletResponse response) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Getting notifications based on AuthToken {}", authToken);
		try {
			ArrayList<RestUserNotification> notifications = 
					new ArrayList<RestUserNotification>();
			for (UserNotification notification: userNotificationService.
					getUserNotificationsByAuthToken(authToken)) {
				notifications.add(RestObjectToModelObjectMapper.
						UserNotificationToRestUserNotification(notification));
			}
			Map<String, Object> responseMessage = ResponseMessage.
					successResponse(HttpServletResponse.SC_OK);
			responseMessage.put("data", notifications);
			return responseMessage;
		} catch (Exception e) {
			LOGGER.warn("Exception occurred when trying"
					+ " to load notification", e);
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					"Unable to load notifications", response);
		}
	}
	
	/**
	 * Marking notification state as read.
	 * @param request    the HTTP request.
	 * @param response   the HTTP response.
	 * @param id         the notification id.
	 * @return           the response of the notification state.
	 */
	@RequestMapping(value = "/userNotifications/{id}/read", 
			method = RequestMethod.POST)
	public final Map<String, Object> readUserNotifications(
			final HttpServletRequest request, 
			final HttpServletResponse response, 
			@PathVariable final Long id) {
		String authToken = request.getHeader("authToken");
		LOGGER.debug("Updating notification based on ID and "
				+ "supplied AuthToken {}", authToken);
		try {
			Optional<User> user = userService.
					getValidUserForAuthToken(authToken);
			if (user.isPresent()) {
				Optional<UserNotification> userNotification = 
						userNotificationService.getByAuthTokenAndNotificationId(
								authToken, id);
				if (userNotification.isPresent()) {
					userNotification.get().setRead(true);
					userNotificationService.save(userNotification.get());
					Map<String, Object> responseMessage = ResponseMessage.
							successResponse(HttpServletResponse.SC_OK);
					responseMessage.put("data", RestObjectToModelObjectMapper.
							UserNotificationToRestUserNotification(
									userNotificationService.save(
									userNotification.get())));
					return responseMessage;
				}
			}
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
						"Supplied notification id is not correct", response);
		} catch (Exception e) {
			LOGGER.warn("Exception occurred when trying "
					+ "to load notification", e);
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					e.getMessage(), response);
		}
	}
}
