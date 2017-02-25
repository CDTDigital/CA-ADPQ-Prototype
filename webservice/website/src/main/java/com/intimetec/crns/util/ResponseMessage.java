/**
 * 
 */
package com.intimetec.crns.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shiva.dixit
 *
 */
public class ResponseMessage {
	public static Map<String, Object> successResponse(int statusCode){
		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		responseMap.put("responseStatus", "SUCCESS");
		responseMap.put("statusCode", statusCode);
		return responseMap;
	}
	
	public static Map<String, Object> failureResponse(int statusCode, String message){
		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		responseMap.put("responseStatus", "FAILURE");
		responseMap.put("statusCode", statusCode);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);
		responseMap.put("data", data);
		return responseMap;
	}
}
