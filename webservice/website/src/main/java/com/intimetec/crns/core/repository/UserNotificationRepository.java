package com.intimetec.crns.core.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.UserNotification;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
	Optional<UserNotification> getById(long id);
    Collection<UserNotification> getByUserId(long userId);
    Collection<UserNotification> getByNotificationId(long notificationId);
    
    @Query(value = "Select n From UserNotification n where n.id IN (Select un.notification.id from UserNotification un where un.userId =?1 ) ")
    Collection<UserNotification> findNotificationsByUserId(long userId);
    
}