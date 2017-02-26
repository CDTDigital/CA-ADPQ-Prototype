package com.intimetec.crns.core.service.notification;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Optional<Notification> getById(long id) {
        LOGGER.debug("Getting Notification={}", id);
        return Optional.ofNullable(notificationRepository.findOne(id));
    }

	@Override
	public Collection<Notification> getBySentBy(long userId) {
		LOGGER.debug("Getting Notifications based on who sent it ={}", userId);
        return notificationRepository.getBySentById(userId);
	}

	@Override
	public Collection<Notification> getByCity(String city) {
		LOGGER.debug("Getting Notifications based on city ={}", city);
        return notificationRepository.getByCity(city);
	}

	@Override
	public Collection<Notification> getByZipCode(String zipCode) {
		LOGGER.debug("Getting Notifications based on zipCode ={}", zipCode);
        return notificationRepository.getByZipCode(zipCode);
	}

	@Override
	public Collection<Notification> getAll() {
		LOGGER.debug("Getting all notification");
        return notificationRepository.findAll(new Sort(Direction.DESC, "sentTime"));
	}

	@Override
	public Notification save(Notification notification) {
		LOGGER.debug("Saving all notification");
		return notificationRepository.save(notification);
	}

}
