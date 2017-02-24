/**
 * 
 */
package com.intimetec.crns.core.authentication;

import java.io.PrintWriter;
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
import com.intimetec.crns.core.models.UserRole;
import com.intimetec.crns.core.service.userdevice.UserDeviceServiceImpl;
import com.intimetec.crns.util.ResponseMessage;

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
		
		//Generate authToke
		String authToken = null;
        
		if(currentUser.getDeviceInfo() != null && currentUser.getRole() == UserRole.USER){
			
			authToken = new BCryptPasswordEncoder().encode(authUser.getUserName()+System.currentTimeMillis());
			
	        //Save Device Information
	        UserDevice userDevice = new UserDevice(authUser, 
	        		currentUser.getDeviceInfo().getDeviceId(), 
	        		currentUser.getDeviceInfo().getDeviceType(), 
	        		currentUser.getDeviceInfo().getDeviceToken(), 
	        		authToken);
	        
	        UserDeviceService.save(userDevice);
		}
		
		//Generate reponseMessage for Successful Login
        Map<String, Object> responseMessage = generateResponseMessage(authUser, authToken);
		
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
        //write response message in JSON format
        writeResponseMessage(response, responseMessage);
	}

	private Map<String, Object> generateResponseMessage(User authUser, String authToken) {
		Map<String, Object> responseMap = ResponseMessage.successResponse(HttpServletResponse.SC_OK);
		responseMap.put("data", authUser);
		responseMap.put("authToken", authToken);
		return responseMap;
	}

	private void writeResponseMessage(HttpServletResponse response, Map<String, Object> responseMessage)
			throws java.io.IOException {
		PrintWriter writer = response.getWriter();
		writer.write(mapper.writeValueAsString(responseMessage));
	}
}
