/**
 * 
 */
package com.intimetec.crns.core.models;

/**
 * @author harish.vaishnav
 *
 */
public class Location {

	private String streetNumber;

	private String cityName;

	private String route;

	private String county;

	private String stateLongName;

	private String stateShortName;

	private String country;

	private String postalCode;

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @return the stateShortName
	 */
	public String getStateShortName() {
		return stateShortName;
	}

	/**
	 * @param stateShortName the stateShortName to set
	 */
	public void setStateShortName(String stateShortName) {
		this.stateShortName = stateShortName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the streetNumber
	 */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * @param streetNumber the streetNumber to set
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the stateLongName
	 */
	public String getStateLongName() {
		return stateLongName;
	}

	/**
	 * @param stateLongName the stateLongName to set
	 */
	public void setStateLongName(String stateLongName) {
		this.stateLongName = stateLongName;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	public Location(String country,String stateLongName,String stateShortName, String county,String route,
			String streetNumber, String postalCode, String cityName)
	{
		this.country = country;
		this.stateLongName = stateLongName;
		this.stateShortName = stateShortName;
		this.county = county;
		this.route = route;
		this.cityName = cityName;
		this.postalCode = postalCode;
		this.streetNumber = streetNumber;
	}

}
