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
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
	
	public AuthFilter() {
	    super("/login");
	}
	
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
                return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
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
/*
    @Autowired
    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }
    
    @Autowired
    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }*/
}