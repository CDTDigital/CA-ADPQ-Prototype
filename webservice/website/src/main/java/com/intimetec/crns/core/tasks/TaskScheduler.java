package com.intimetec.crns.core.tasks;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author In Time Tec
 */
@Component
public class TaskScheduler {
	
	/**
	 * Schedule the tasks to run at the specified interval.
	 */
	@Scheduled(cron = "${cron.expression}")
	public final void scheduledTask() {
        System.out.println("Method executed at every minute. "
        		+ "Current time is :: " + new Date());
    }
}
