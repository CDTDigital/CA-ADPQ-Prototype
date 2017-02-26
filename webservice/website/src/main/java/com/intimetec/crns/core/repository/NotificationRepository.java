package com.intimetec.crns.core.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.Notification;

/**
 * @author shiva.dixit
 */
@Repository("notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, 
Long> {
	Collection<Notification> getBySentById(long userId);

	Collection<Notification> getByCity(String city);

	Collection<Notification> getByZipCode(String zipCode);

}