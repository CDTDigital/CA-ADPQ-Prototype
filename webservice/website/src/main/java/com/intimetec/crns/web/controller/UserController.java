package com.intimetec.crns.web.controller;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.repository.UserNotificationOptionRepository;
import com.intimetec.crns.core.repository.UserRepository;
import com.intimetec.crns.core.service.user.UserService;
import com.intimetec.crns.util.ResponseMessage;

@RequestMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE )
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserNotificationOptionRepository userNotificationOptionRepository;

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping("/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        LOGGER.debug("Getting user page for user={}", id);
        return new ModelAndView("user", "user", userService.getUserById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Collection<User> getUserCreatePage() {
    	Collection<User> users = userService.getAllUsers();
    	return users;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Map<String, Object> createUser(@RequestBody User user) {
    	System.out.println("User: "+user);
        LOGGER.debug("Processing user creation");
        try {
            userService.create(user);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            return ResponseMessage.failureResponse(HttpServletResponse.SC_BAD_REQUEST, "Not valid inputs");
        }
        // ok, redirect
        return ResponseMessage.successResponse(HttpServletResponse.SC_OK);
    }

}
