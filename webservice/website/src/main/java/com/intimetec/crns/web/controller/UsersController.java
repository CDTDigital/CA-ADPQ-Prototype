package com.intimetec.crns.web.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.service.user.UserService;

@RestController
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE )
    public Collection<User> getUsersPage() {
        LOGGER.debug("Getting users page");
        return userService.getAllUsers();
    }


}
