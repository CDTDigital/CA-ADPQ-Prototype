package com.intimetec.crns.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

/**
 * @author In Time Tec
 *
 */
public class ResponseMessage {
	
	/**
	 * @param statusCode the status code.
	 * @return Returns the success response message.
	 */
	public static Map<String, Object> successResponse(final int statusCode) {
		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		responseMap.put("responseStatus", "SUCCESS");
		responseMap.put("statusCode", statusCode);
		return responseMap;
	}
	
	/**
	 * @param statusCode the status code.
	 * @param message    the response message.
	 * @param response   the HTTP servlet response.
	 * @return Returns the failure response message.
	 */
	public static Map<String, Object> failureResponse(final int statusCode, 
			final String message, final HttpServletResponse response) {
		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		responseMap.put("responseStatus", "FAILURE");
		responseMap.put("statusCode", statusCode);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);
		responseMap.put("data", data);
		response.setStatus(statusCode);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return responseMap;
	}
}
