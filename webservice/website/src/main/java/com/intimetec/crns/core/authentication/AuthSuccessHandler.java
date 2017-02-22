/**
 * 
 */
package com.intimetec.crns.core.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.core.models.CurrentUser;
import com.intimetec.crns.core.models.User;

/**
 * @author shiva.dixit
 *
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

	private final ObjectMapper mapper;

	@Autowired
	AuthSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws javax.servlet.ServletException, java.io.IOException {
		LOGGER.debug("Inside Authentication Sucess Handler");
		HttpSession session = request.getSession();
		User authUser = ((CurrentUser) authentication.getPrincipal()).getUser();
        session.setAttribute("username", authUser.getUserName());
        session.setAttribute("authorities", authentication.getAuthorities());
 
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

		LOGGER.info(authentication.getPrincipal() + " got is connected ");

		LOGGER.debug("User: " + authentication.getPrincipal());
		System.out.println("User: " + authentication.getPrincipal());

		//super.onAuthenticationSuccess(request, response, authentication);
	}
}
