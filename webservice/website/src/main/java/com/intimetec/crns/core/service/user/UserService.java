package com.intimetec.crns.core.service.user;

import java.util.Collection;
import java.util.Optional;

import com.intimetec.crns.web.models.User;

public interface UserService {
	Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(User user);
}