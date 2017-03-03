package com.intimetec.crns.core.noaa.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.intimetec.crns.core.models.County2ZipMapping;
import com.intimetec.crns.core.models.LastSync;
import com.intimetec.crns.core.models.Notification;
import com.intimetec.crns.core.models.User;
import com.intimetec.crns.core.noaa.Feed;
import com.intimetec.crns.core.noaa.NoaaWeatherAlertsFeeds;
import com.intimetec.crns.core.repository.LastSyncTimeRepository;
import com.intimetec.crns.core.service.notification.NotificationService;
import com.intimetec.crns.core.service.user.UserService;

/**
 * @author In Time Tec
 */
@Component
public class TaskScheduler {
	@Autowired
	NoaaWeatherAlertsFeeds noaaWeatherAlertsFeeds;
	@Autowired
	UserService userService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	LastSyncTimeRepository lastSyncTimeRepository;

	/**
	 * Schedule the tasks to run at the specified interval.
	 */
	@Scheduled(cron = "${cron.expression}")
	public final void scheduledTask() {
		try {
			Feed feed = noaaWeatherAlertsFeeds.parseFeeds();
			User user =userService.getUserByEmail("w-nws.webmaster@noaa.gov").get();
			List<Notification> notificationList = new ArrayList<Notification>();
			for (Feed.Entry entry : feed.getEntry()) {
				for (County2ZipMapping county : entry.getCoutyMapping2Zip()) {
					Notification notification = new Notification(
							user, entry.getPublished(),
							county.getCountyName(), county.getCountyName(), county.getLatitude(), county.getLongitude(), county.getZipCode(),
							entry.getTitle(), entry.getSummary(), entry.getExpires());
					notificationList.add(notification);
				}
			}
			
			notificationService.saveAll(notificationList);
			
			for(Notification notification: notificationList){
				notificationService.sendNotification(notification);
			}
			
			Optional<LastSync> lastSync = lastSyncTimeRepository.findLatestNoaaSyncTime();
			if(lastSync.isPresent()){
				lastSync.get().setNoaaLastSyncTime(new Date());
				lastSyncTimeRepository.save(lastSync.get());
			}
			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
