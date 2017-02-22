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

public class AuthFilter extends UsernamePasswordAuthenticationFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  throws AuthenticationException{
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
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
                LoginRequest loginRequest = mapper.readValue(parsedReq, LoginRequest.class);
                System.out.println("Login Request: "+ loginRequest);
                LOGGER.debug("Login Request: "+ loginRequest);
                request.getSession().setAttribute("loginRequest", loginRequest);
                Authentication authentication =  getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
                
                User authUser = ((CurrentUser) authentication.getPrincipal()).getUser();
                if(!authUser.isStatus()){
                	throw new InternalAuthenticationServiceException("User Account is locked");
                }
                
                if(loginRequest.getDeviceId() !=null && !loginRequest.getDeviceId().isEmpty()) {
                	if(authUser.getUserRole().equals(UserRole.USER)) {
                		((CurrentUser)authentication.getPrincipal()).setDeviceInfo(loginRequest.getDeviceId(), loginRequest.getDeviceType(), loginRequest.getDeviceToken());
                	} else {
                    	LOGGER.info("Invalid Login: User Role - "+authUser.getUserRole()+", Device ID: "+loginRequest.getDeviceId());
                		throw new InternalAuthenticationServiceException("Invalid login request");
                	}
                } else if(!authUser.getUserRole().equals(UserRole.ADMIN)) {
                	LOGGER.info("Invalid Login: User Role - "+authUser.getUserRole()+", Device ID: "+loginRequest.getDeviceId());
                	throw new InternalAuthenticationServiceException("Invalid login request");
                }
                return authentication;
            }
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
            throw new InternalAuthenticationServiceException("Failed to parse authentication request body");
        }
        return null;
    }

	@Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}