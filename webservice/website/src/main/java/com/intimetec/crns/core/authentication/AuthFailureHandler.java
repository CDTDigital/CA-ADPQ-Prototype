package com.intimetec.crns.core.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.util.ResponseMessage;

/**
 * {@code AuthFailureHandler} class to handle the authentication failure.
 *  @author In Time Tec
 */
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	/**
	 * Framework to parse JSON into Java objects.
	 */ 
	private final ObjectMapper mapper;
	/**
	 * @param messageConverter the converter to read and write JSON
	 * using {@link ObjectMapper}
	 */
	@Autowired
	AuthFailureHandler(final MappingJackson2HttpMessageConverter 
			messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public final void onAuthenticationFailure(final HttpServletRequest request, 
            final HttpServletResponse response,
		    final AuthenticationException exception) throws IOException, 
	       ServletException {

		Map<String, Object> responseMap = ResponseMessage.failureResponse(
				HttpServletResponse.SC_UNAUTHORIZED,
				exception.getMessage(), response);
		PrintWriter writer = response.getWriter();
		writer.write(mapper.writeValueAsString(responseMap));
		writer.flush();
	}
}
