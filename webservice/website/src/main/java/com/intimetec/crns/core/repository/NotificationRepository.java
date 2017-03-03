package com.intimetec.crns.core.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.Notification;

/**
 * @author In Time Tec
 */
@Repository("notificationRepository")
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, 
Long> {
	Collection<Notification> getBySentById(long userId);

	Collection<Notification> getByCity(String city);

	Collection<Notification> getByZipCode(String zipCode);

}