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

@RestController
public class LocationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
	@Autowired
	private UserLocationService userlocationService;

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public Map<String, Object> getPostalCode(@RequestParam(value="lat") String latitude,
			@RequestParam(value="lng") String longitude) {
		LOGGER.info("Getting postal code");
		Map<String, Object> responseMap = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
		try {
			responseMap.put("data", userlocationService.getLocationDetails(latitude, longitude));
			return responseMap;
		} catch (InvalidLocationCoordinatesException e) {
			return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST,
					e.getMessage());
		}
	}
}