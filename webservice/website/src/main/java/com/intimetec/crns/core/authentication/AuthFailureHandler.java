/**
 * 
 */
package com.intimetec.crns.core.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author shiva.dixit
 *
 */
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private final ObjectMapper mapper;

	@Autowired
	AuthFailureHandler(MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
        responseMap.put("responseStatus", "FAILURE");
        responseMap.put("statusCode", HttpServletResponse.SC_UNAUTHORIZED);
        responseMap.put("message", exception.getMessage());
        
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(responseMap));
        writer.flush();
    }
}
