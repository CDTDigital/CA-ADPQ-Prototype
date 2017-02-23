/**
 * 
 */
package com.intimetec.crns.core.authentication;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.core.models.CurrentUser;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.service.userdevice.UserDeviceServiceImpl;

/**
 * @author shiva.dixit
 *
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

	private final ObjectMapper mapper;
	@Autowired
	UserDeviceServiceImpl UserDeviceService;

	@Autowired
	AuthSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws javax.servlet.ServletException, java.io.IOException {
		LOGGER.debug("Inside Authentication Sucess Handler");
		HttpSession session = request.getSession();
		
		CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
		User authUser = currentUser.getUser();
        session.setAttribute("username", authUser.getUserName());
        session.setAttribute("authorities", authentication.getAuthorities());
        
        LOGGER.info(authentication.getPrincipal() + " got is connected ");
		LOGGER.info("User: " + authUser);
		LOGGER.info("Device Info: " + currentUser.getDeviceInfo());
        
		//Generate authToke for mobile devices
		String authToken = new BCryptPasswordEncoder().encode(authUser.getUserName()+System.currentTimeMillis());
		
        //Generate reponseMessage for Successful Login
        Map<String, Object> responseMessage = generateResponseMessage(authUser, authToken);
        
        //Save Device Information
        UserDevice userDevice = new UserDevice(authUser, 
        		currentUser.getDeviceInfo().getDeviceId(), 
        		currentUser.getDeviceInfo().getDeviceType(), 
        		currentUser.getDeviceInfo().getDeviceToken(), 
        		authToken);
        
        UserDeviceService.save(userDevice);
 
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
        //write response message in JSON format
        writeResponseMessage(response, responseMessage);
	}

	private Map<String, Object> generateResponseMessage(User authUser, String authToken) {
		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		responseMap.put("responseStatus", "SUCCESS");
		responseMap.put("statusCode", HttpServletResponse.SC_OK);
		responseMap.put("authToken", authToken);
		responseMap.put("setupAccount", new Boolean((authUser.getUserNotificationOptions() == null) ? false : true));
		responseMap.put("firstName", authUser.getFirstName());
		responseMap.put("lastName", authUser.getLastName());

		return responseMap;
	}

	private void writeResponseMessage(HttpServletResponse response, Map<String, Object> responseMessage)
			throws java.io.IOException {
		PrintWriter writer = response.getWriter();
		writer.write(mapper.writeValueAsString(responseMessage));
	}
}
