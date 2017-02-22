/**
 * 
 */
package com.intimetec.crns.core.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.core.models.CurrentUser;
import com.intimetec.crns.core.models.User;

/**
 * @author shiva.dixit
 *
 */
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

    private final ObjectMapper mapper;

    @Autowired
    AuthSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.mapper = messageConverter.getObjectMapper();
    }

    @Override
   	public void onAuthenticationSuccess(HttpServletRequest request,   HttpServletResponse response, Authentication authentication) throws IOException  {
        LOGGER.debug("Inside Authentication Sucess Handler");
        CurrentUser userDetails = (CurrentUser) authentication.getPrincipal();
        User user = userDetails.getUser();

        LOGGER.info(userDetails.getUsername() + " got is connected ");

        LOGGER.debug("User: "+user);
    }
}
