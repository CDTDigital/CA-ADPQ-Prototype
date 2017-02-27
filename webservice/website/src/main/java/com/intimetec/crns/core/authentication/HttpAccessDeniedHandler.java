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
	/**
	 * To log the application messages. 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(
			HttpAccessDeniedHandler.class);
	/**
	 * Framework to parse JSON into Java objects.
	 */
	private final ObjectMapper mapper;
	/**
	 * @param messageConverter the converter to read and write JSON
	 * using {@link ObjectMapper}.
	 */
	@Autowired
	HttpAccessDeniedHandler(final MappingJackson2HttpMessageConverter 
			messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public final void handle(final HttpServletRequest request, 
			final HttpServletResponse response, 
			final AccessDeniedException authException)
			throws IOException {
		Enumeration<String> params = request.getAttributeNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			LOGGER.debug(param, request.getAttribute(param));
		}

		Map<String, Object> responseMap = ResponseMessage.failureResponse(
				HttpServletResponse.SC_FORBIDDEN,
				authException.getMessage(), response);

		PrintWriter writer = response.getWriter();
		writer.write(mapper.writeValueAsString(responseMap));
		writer.flush();
	}
}
