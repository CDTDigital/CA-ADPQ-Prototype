package com.intimetec.crns.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.intimetec.crns.core.service.location.LocationService;
import com.intimetec.crns.core.service.location.LocationServiceImpl;
import com.intimetec.crns.util.ResponseMessage;

@RestController
public class LocationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
	private LocationService location = new LocationServiceImpl();

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public Map<String, Object> getPostalCode(@RequestParam(value="latlng") String latlng) {
		LOGGER.info("Getting postal code");
		Map<String, Object> responseMap = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
		responseMap.put("data", location.getLocationDetails(latlng));
		return responseMap;
	}
}