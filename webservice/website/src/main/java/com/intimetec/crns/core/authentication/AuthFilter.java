package com.intimetec.crns.core.authentication;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.core.models.CurrentUser;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.models.UserRole;

/**
 * @author shiva.dixit
 *
 */
public class AuthFilter extends UsernamePasswordAuthenticationFilter {
	/**
	 * To log the application messages. 
	 */
	private static final Logger LOGGER = LoggerFactory.
	       getLogger(AuthFilter.class);

	@Override
	public final Authentication attemptAuthentication(HttpServletRequest 
			request, HttpServletResponse response)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported" + ": " 
			+ request.getMethod());
		}
		try {
			BufferedReader reader = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			String parsedReq = sb.toString();
			if (parsedReq != null) {
				ObjectMapper mapper = new ObjectMapper();
				LoginRequest loginRequest = mapper.readValue(parsedReq, 
						LoginRequest.class);
				LOGGER.debug("Login Request: " + loginRequest);
				request.getSession().setAttribute("loginRequest", loginRequest);
				Authentication authentication = getAuthenticationManager()
						.authenticate(new UsernamePasswordAuthenticationToken(
								loginRequest.getUserName(),
								loginRequest.getPassword()));

				User authUser = ((CurrentUser) authentication.
						getPrincipal()).getUser();
				if (!authUser.isEnabled()) {
					throw new InternalAuthenticationServiceException(
							"User Account is locked");
				}
				
				if (loginRequest.getDeviceId() != null && authUser.getUserRole() == UserRole.ADMIN) {
					throw new InternalAuthenticationServiceException(
							"Please user Web application for adminitrative purpose.");
				}

				if (loginRequest.getDeviceId() != null && !loginRequest.
						getDeviceId().isEmpty()) {
					((CurrentUser) authentication.getPrincipal()).setDeviceInfo(
							loginRequest.getDeviceId(),
							loginRequest.getDeviceType(), 
							loginRequest.getDeviceToken());
				}
				return authentication;
			}
		} catch (IOException ex) {
			LOGGER.debug(ex.getMessage());
			throw new InternalAuthenticationServiceException(
					"Failed to parse authentication " + "request body");
		}
		return null;
	}

	@Autowired
	@Override
	public final void setAuthenticationManager(
			 AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
}