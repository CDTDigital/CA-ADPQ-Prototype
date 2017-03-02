package com.intimetec.crns.core.models.restmodels;

/**
 * Rest model class for User Locations table.
 * @author In Time Tec
 */
public class RestLocation {
	/**
	 * Id of the user_location.
	 */
	private long id;

	/**
	 * Address Line 1 of the User.
	 */
	private String addressLine1;
	
	/**
	 * Address Line 2 of the User.
	 */	
	private String addressLine2;

	/**
	 * City of the User.
	 */
	private String city;

	/**
	 * Zip code of the User.
	 */
	private String zipCode;
	
	/**
	 * Place Id of the User.
	 */
	private String placeId;

	/**
	 * Latitude of the User's location.
	 */
	private String latitude;
	
	/**
	 * Longitude of the User's location.
	 */
	private String longitude;
	
	/**
	 * Current location of the User.
	 */
	private boolean currentLocation;

	/**
	 * @return id of the User location.
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id of the User location.
	 */
	public final void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return the address line 1 of the User.
	 */
	public final String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1 the address line 1 of the User.
	 */
	public final void setAddressLine1(final String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the address line 2 of the User.
	 */
	public final String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2 the address line 2 of the User.
	 */
	public final void setAddressLine2(final String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the city of the User's location.
	 */
	public final String getCity() {
		return city;
	}

	/**
	 * @param city the city of the User's location.
	 */
	public final void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zip code of the User's location.
	 */
	public final String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zip code of the User's location.
	 */
	public final void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the place id of the User's location.
	 */
	public final String getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId the place id of the User's location.
	 */
	public final void setPlaceId(final String placeId) {
		this.placeId = placeId;
	}

	/**
	 * @return the latitude of the User's location.
	 */
	public final String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude of the User's location.
	 */
	public final void setLatitude(final String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude of the User's location.
	 */
	public final String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude of the User's location.
	 */
	public final void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return true if the given location is the current location of the User.
	 */
	public final boolean isCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation true if it's the current location.
	 */
	public final void setCurrentLocation(final boolean currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	@Override
	public final String toString() {
        return "Location{" + ", "
        		+ "id=" + id + ", "
        	    + "addressLine1=" + addressLine1 
        	    + ", addressLine2=" + addressLine2 
                + ", city=" + city 
                + ", zipCode=" + zipCode 
                + ", placeId=" + placeId 
                + ", lat=" + latitude 
                + ", log=" + longitude 
                +  ", currentLocation=" + currentLocation 
                + '}';
    }
}
