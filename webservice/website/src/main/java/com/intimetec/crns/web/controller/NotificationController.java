package com.intimetec.crns.web.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

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
import com.intimetec.crns.core.restmodels.RestNotification;
import com.intimetec.crns.core.service.notification.NotificationService;
import com.intimetec.crns.util.ResponseMessage;
import com.intimetec.crns.util.RestObjectToModelObjectMapper;

@RequestMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class NotificationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private NotificationService notificationService;

	/**
	 * Saving a notification published by Admin, after saving the same
	 * notification will be sent to various users belongs to the same
	 * zip-code/postal-code
	 * 
	 * @param notification
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public Map<String, Object> sendNotification(@RequestBody Notification notification) {
		LOGGER.debug("Processing notification to save and send");
		try {
			notification = notificationService.save(notification);
			Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			response.put("data", notification);
			return response;
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Exception occurred when trying to save the notification", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
		}
	}

	/**
	 * Getting list of all notification 
	 * @param
	 * @return
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUserByRoles(principal, {'ADMIN', 'USER'})")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map<String, Object> getNotifications() {
		LOGGER.debug("Getting all notifications {}");
		try {
			ArrayList<RestNotification> notifications = new ArrayList<RestNotification>();
			for(Notification notification: notificationService.getAll()){
				notification.getSentBy().setPassword(null);
				notifications.add(RestObjectToModelObjectMapper.NotificationToRestNotification(notification));
			}
			Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
			response.put("data", notifications);
			return response;
		} catch (Exception e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Unable to load notification list");
		}
	}

	/**
	 * Getting notification detail based on supplied ID
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("@currentUserServiceImpl.canAccessUserByRoles(principal, {'ADMIN', 'USER'})")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Map<String, Object> getNotification(@PathVariable Long id) {
		LOGGER.debug("Getting notification based on ID {}", id);
		try {
			Optional<Notification> notification = notificationService.getById(id);
			if (notification.isPresent()) {
				Map<String, Object> response = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
				notification.get().getSentBy().setPassword(null);
				response.put("data", RestObjectToModelObjectMapper.NotificationToRestNotification(notification.get()));
				return response;
			} else {
				throw new InvalidNotificatioException();
			}
		} catch (Exception e) {
			LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					"Unable to load notification details");
		}
	}
}
