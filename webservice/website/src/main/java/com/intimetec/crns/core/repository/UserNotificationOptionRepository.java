package com.intimetec.crns.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.UserNotificationOptions;

@Repository
public interface UserNotificationOptionRepository extends JpaRepository<UserNotificationOptions, Long> {
    Optional<UserNotificationOptions> findOneByUserId(long userId);
}