package com.intimetec.crns.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.web.models.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
}
