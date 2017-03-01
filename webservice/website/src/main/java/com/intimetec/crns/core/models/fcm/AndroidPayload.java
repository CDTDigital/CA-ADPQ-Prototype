package com.intimetec.crns.core.models.fcm;

import java.util.Date;
import java.util.List;

public class AndroidPayload {
	private String priority = "high";

	private List<String> registration_ids;
	
	private Data data;

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
		this.data = new Data(title, body, notificationId, notId);
	}

	public AndroidPayload() {
	}

	public AndroidPayload(List<String> registrationIds, String title, String body, 
			long notificationId, long notId) {
		this.registration_ids = registrationIds;
		this.data = new Data(title, body, notificationId, notId);
	}

	class Data {
		private String title;
		private String body;
		private String color = "#014D81";
		private String sound = "default";
		private String type = "alert";
		private String icon = "icon";
		private long notificationId;
		private long notId;
		private Date time;
		
		public Data(){
			
		}
		
		public Data(String title, String body, long notificationId, long notId){
			this.title = title;
			this.body = body;
			this.notificationId = notificationId;
			this.notId = notId;
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

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public long getNotificationId() {
			return notificationId;
		}

		public void setNotificationId(long notificationId) {
			this.notificationId = notificationId;
		}

		public long getNotId() {
			return notId;
		}

		public void setNotId(int notId) {
			this.notId = notId;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}
	}
}
