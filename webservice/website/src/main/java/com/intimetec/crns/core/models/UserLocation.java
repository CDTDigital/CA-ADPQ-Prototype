package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for User Locations table
 * 
 * @author shiva.dixit
 */

@Entity
@Table(name = "user_locations")
public class UserLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "city")
	private String city;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "place_id")
	private String placeId;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "current_location")
	private boolean currentLocation;

	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

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

	public boolean isCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(boolean currentLocation) {
		this.currentLocation = currentLocation;
	}

	@Override
	public String toString() {
		return "UserLocation{" + "id=" + id + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
				+ ", city=" + city + ", zipCode=" + zipCode + ", placeId=" + placeId + ", lat=" + latitude + ", log="
				+ longitude + ", currentLocation=" + currentLocation + '}';
	}

	public UserLocation() {

	}

	public UserLocation(String stateShortName, String county, String route, String streetNumber, String zipCode,
			String cityName, String lat, String lng, String placeId) {
		this.addressLine1 = streetNumber;
		this.addressLine2 = route;
		this.city = cityName;
		this.zipCode = zipCode;
		this.latitude = lat;
		this.longitude = lng;
		this.placeId = placeId;
	}
}
