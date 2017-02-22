package com.intimetec.crns.core.service.currentuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.CurrentUser;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.service.user.UserService;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);
    @Autowired
    private UserService userService;

    @Override
    public CurrentUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        LOGGER.debug("Authenticating user with userName={}", userName);
        User user = userService.getUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username=%s was not found", userName)));
        return new CurrentUser(user);
    }

}
