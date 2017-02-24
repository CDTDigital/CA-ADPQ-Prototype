/**
 * 
 */
package com.intimetec.crns.core.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.util.ResponseMessage;

/**
 * @author shiva.dixit
 *
 */
@Component
public class HttpAccessDeniedHandler implements AccessDeniedHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpAccessDeniedHandler.class);	
	
	private final ObjectMapper mapper;

	@Autowired
	HttpAccessDeniedHandler(MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException authException) throws IOException {
    	Enumeration<String> params = request.getAttributeNames();
    	while(params.hasMoreElements()){
    		String param = params.nextElement();
        	LOGGER.debug(param, request.getAttribute(param));
    	}
    	       
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		Map<String, Object> responseMap = ResponseMessage.failureResponse(HttpServletResponse.SC_FORBIDDEN,
				authException.getMessage());

		PrintWriter writer = response.getWriter();
		writer.write(mapper.writeValueAsString(responseMap));
		writer.flush();
    }
}
