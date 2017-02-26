package com.intimetec.crns.core.restmodels;

/**
 * Rest model class for User Locations table
 * @author shiva.dixit
 */
public class RestLocation {
	private long id;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String city;

	private String zipCode;
	
	private String placeId;
	
	private String latitude;
	
	private String longitude;
	
	private boolean currentLocation;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
        return "Location{" +
        		", id=" + id +
                ", addressLine1=" + addressLine1 +
                ", addressLine2=" + addressLine2 +
                ", city=" + city +
                ", zipCode=" + zipCode +
                ", placeId=" + placeId +
                ", lat=" + latitude +
                ", log=" + longitude +
                ", currentLocation=" + currentLocation +
                '}';
    }
}
