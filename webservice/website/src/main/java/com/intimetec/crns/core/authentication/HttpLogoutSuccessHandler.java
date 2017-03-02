package com.intimetec.crns.core.authentication;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * {@code HttpLogoutSuccessHandler} class to handle the successful logout.
 *  @author shiva.dixit
 */
@Component
public class HttpLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
	public final void onLogoutSuccess(final HttpServletRequest request,
			final HttpServletResponse response, 
			final Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();
    }
}