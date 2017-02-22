/**
 * 
 */
package com.intimetec.crns.core.authentication;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.intimetec.crns.web.controller.CurrentUserControllerAdvice;

/**
 * @author shiva.dixit
 *
 */
@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserControllerAdvice.class);	
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
    	Enumeration<String> params = request.getAttributeNames();
    	while(params.hasMoreElements()){
    		String param = params.nextElement();
        	LOGGER.debug(param, request.getAttribute(param));
    	}
    	
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
