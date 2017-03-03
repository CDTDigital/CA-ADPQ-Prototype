package com.intimetec.crns.core.service.notification;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.intimetec.crns.core.models.Notification;

/**
 * @author In Time Tec
 */
public interface NotificationService {
	Optional<Notification> getById(long id);

	Collection<Notification> getBySentBy(long userId);
    
    Collection<Notification> getByCity(String city);
    
    Collection<Notification> getByZipCode(String zipCode);

    Collection<Notification> getAll();

    Notification save(Notification notification);
    
    void saveAll(List<Notification> notificationList);
    
    void sendNotification(Notification notification);
}
