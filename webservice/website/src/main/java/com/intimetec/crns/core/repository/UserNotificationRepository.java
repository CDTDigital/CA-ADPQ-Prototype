package com.intimetec.crns.core.repository;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.UserNotification;

/**
 * @author shiva.dixit
 */
@Repository
@Transactional
public interface UserNotificationRepository extends 
JpaRepository<UserNotification, Long> {
	Optional<UserNotification> getById(long id);
    Collection<UserNotification> getByUserId(long userId);
    Collection<UserNotification> getByNotificationId(long notificationId);
    Optional<UserNotification> getByUserIdAndNotificationId(long userId, long id);
    
   /* @Query(value = "Select n From UserNotification n where n.id IN ("
    		+ "Select un.NotificationId from UserNotification un where "
    		+ "un.userId =?1 ) ")
    Collection<UserNotification> findNotificationsByUserId(long userId);*/
    
}