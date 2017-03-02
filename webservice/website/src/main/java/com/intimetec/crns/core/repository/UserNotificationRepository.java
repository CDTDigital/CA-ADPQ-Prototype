package com.intimetec.crns.core.repository;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.UserNotification;

/**
 * @author In Time Tec
 */
@Repository
@Transactional
public interface UserNotificationRepository extends 
JpaRepository<UserNotification, Long> {
	Optional<UserNotification> getById(long id);
    Collection<UserNotification> getByUserIdOrderByNotificationSentTimeDesc(long userId);
    Collection<UserNotification> getByNotificationIdOrderByNotificationSentTimeDesc(long notificationId);
    Optional<UserNotification> getByUserIdAndNotificationId(long userId, long id);
      
}