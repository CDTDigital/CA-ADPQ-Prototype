package com.intimetec.crns.core.models.fcm;

import java.util.List;

public class IOSPayload {
	private String priority = "high";

	private List<String> registration_ids;
	
	private Data data;
	
	private Notification notification;

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public List<String> getRegistrationIds() {
		return registration_ids;
	}

	public void setRegistrationIds(List<String> registrationIds) {
		this.registration_ids = registrationIds;
	}

	public Data getData() {
		return data;
	}

	public void setData(String title, String body, 
			int notificationId, int notId) {
		this.data = new Data(notificationId);
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(String title, String body) {
		this.notification = new Notification(title, body);
	}

	public IOSPayload() {
	}

	public IOSPayload(List<String> registrationIds, String title, String body, 
			long notificationId) {
		this.registration_ids = registrationIds;
		this.data = new Data(notificationId);
		this.notification = new Notification(title, body);
	}
	
	class Notification {
		private String title;
		private String body;
		private String color = "#014D81";
		private String sound = "default";
		
		public Notification(){
			
		}
		
		public Notification(String title, String body){
			this.title = title;
			this.body = body;
		}
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getSound() {
			return sound;
		}

		public void setSound(String sound) {
			this.sound = sound;
		}
	}

	class Data {
		private String type = "alert";
		private long notificationId;
		
		public Data(){
			
		}
		
		public Data(long notificationId){
			this.notificationId = notificationId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public long getNotificationId() {
			return notificationId;
		}

		public void setNotificationId(long notificationId) {
			this.notificationId = notificationId;
		}
	}
}
