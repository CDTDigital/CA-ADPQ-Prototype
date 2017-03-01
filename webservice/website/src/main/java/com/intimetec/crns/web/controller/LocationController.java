package com.intimetec.crns.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intimetec.crns.core.exceptions.InvalidLocationCoordinatesException;
import com.intimetec.crns.core.service.userLocation.UserLocationService;
import com.intimetec.crns.util.ResponseMessage;

/**
 * @author shiva.dixit
 */
@RestController
public class LocationController {

	/**
	 * To log the application messages. 
	 */
	private static Logger LOGGER = 
			LoggerFactory.getLogger(LocationController.class);
	
	/**
	 * Instance of the class {@code UserLocationService}. 
	 */
	@Autowired
	private UserLocationService userlocationService;

	/**
	 * Instance of the class {@code UserLocationService}. 
	 * @param latitude  the latitude of the location.
	 * @param longitude the longitude of the location.
	 * @param response  the HTTP response.
	 * @return returns  the response.
	 */
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public Map<String, Object> getPostalCode(@RequestParam(
			value = "lat") String latitude,
			@RequestParam(value = "lng") String longitude, 
			HttpServletResponse response) {
		LOGGER.info("Getting postal code");
		Map<String, Object> responseMap = ResponseMessage.successResponse(
				HttpServletResponse.SC_OK);
		try {
			responseMap.put("data", userlocationService.getLocationDetails(
					latitude, longitude));
			return responseMap;
		} catch (InvalidLocationCoordinatesException e) {
			return ResponseMessage.failureResponse(
					HttpServletResponse.SC_BAD_REQUEST,
					e.getMessage(), response);
		}
	}
}