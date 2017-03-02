/**
 * 
 */
package com.intimetec.crns.core.models.restmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author In Time Tec
 *
 */
public class GeoLocation {
	@JsonProperty("Location")
	private Location location;
	
	public class Location {

		@JsonProperty("Coords")
		private Coords cordinates;

		public class Coords {
			@JsonProperty("Latitude")
			private String latitude;
			
			@JsonProperty("Longitude")
			private String longitude;

			public String getLatitude() {
				return latitude;
			}

			public void setLatitude(String latitude) {
				this.latitude = latitude;
			}

			public String getLongitude() {
				return longitude;
			}

			public void setLongitude(String longitude) {
				this.longitude = longitude;
			}
		}
		
		public Coords getCordinates() {
			return cordinates;
		}

		public void setCordinates(Coords cordinates) {
			this.cordinates = cordinates;
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
