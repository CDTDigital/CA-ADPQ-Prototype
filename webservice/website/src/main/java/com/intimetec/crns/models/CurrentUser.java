package com.intimetec.crns.models;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getUserLogin().getPassword(), AuthorityUtils.createAuthorityList(user.getUserRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return user.getId();
    }

    public UserRole getRole() {
        return user.getUserRole();
    }

}
