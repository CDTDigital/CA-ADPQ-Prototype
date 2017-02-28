package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for User Locations table.
 * @author shiva.dixit
 */

@Entity
@Table(name = "user_locations")
public class UserLocation {

	/**
	 * Id of the user_location.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	/**
	 * Id of the User.
	 */
	@Column(name = "user_id")
	private long userId;

	/**
	 * Address Line 1 of the User.
	 */
	@Column(name = "address_line1")
	private String addressLine1;

	/**
	 * Address Line 2 of the User.
	 */
	@Column(name = "address_line2")
	private String addressLine2;

	/**
	 * City of the User.
	 */
	@Column(name = "city")
	private String city;

	/**
	 * Zip code of the User.
	 */
	@Column(name = "zip_code")
	private String zipCode;

	/**
	 * Place Id of the User.
	 */
	@Column(name = "place_id")
	private String placeId;

	/**
	 * Latitude of the User's location.
	 */
	@Column(name = "latitude")
	private String latitude;

	/**
	 * Longitude of the User's location.
	 */
	@Column(name = "longitude")
	private String longitude;

	/**
	 * Current location of the User.
	 */
	@Column(name = "current_location")
	private boolean currentLocation;

	/**
	 * @return id of the User location.
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param l of the User location.
	 */
	public final void setId(final long l) {
		this.id = l;
	}

	/**
	 * @return id of the User.
	 */
	public final long getUserId() {
		return userId;
	}

	/**
	 * @param userId the id of the User.
	 */
	public final void setUserId(final long userId) {
		this.userId = userId;
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
	public final void setCity(final String city) {
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
	public final void setLongitude(final String longitude) {
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
		return "UserLocation {" + "id=" + id + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city 
				+ zipCode + ", placeId=" + placeId + ", " + "lat="
				+ latitude + ", log=" + longitude + ", currentLocation=" 
				+ currentLocation + '}';		
	}

	/**
	 * Creating object of the class {@code UserLocation}.
	 */
	public UserLocation() {

	}
	
	/**
	 * Creating object of the class {@code UserLocation}.
	 * @param streetNumber    the address Line1 of the User.
	 * @param route           the address Line2 of the User.
	 * @param cityName 	      the city of the User.
	 * @param zipCode         the zip code of the User.
	 * @param lat             the latitude of the User's location.
	 * @param lng             the longitude of the User's location.
	 * @param placeId         the place id of the User's location.
	 */
	public UserLocation(final String streetNumber, final String route, 
			final String cityName, final String zipCode, final String lat, 
			final String lng, final String placeId) {
		this.addressLine1 = streetNumber;
		this.addressLine2 = route;
		this.city = cityName;
		this.zipCode = zipCode;
		this.latitude = lat;
		this.longitude = lng;
		this.placeId = placeId;
	}
}
