package com.intimetec.crns.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for county_zip_mapping table.
 * @author In Time Tec
 */
@Entity
@Table(name = "county_zip_mapping")
public class County2ZipMapping {
	/**
	 * Id of the User.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	/**
	 * County name
	 */
	@Column(name = "county_name")
	private String countyName;
	
	/**
	 * ZipCode
	 */
	@Column(name = "zip_code")
	private String zipCode;
	
	/**
	 * latitude
	 */
	@Column(name = "latitude")
	private String latitude;
	
	/**
	 * longitude
	 */
	@Column(name = "longitude")
	private String longitude;
	
	/**
	 * Fips code for County
	 */
	@Column(name = "fips_code")
	private int fipsCode;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Zip code
	 */
	public final String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode 
	 */	
	public final void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the Fips Code of county
	 */
	public final int getFipsCode() {
		return fipsCode;
	}

	/**
	 * @param fipsCode the Fips Code of county
	 */	
	public final void setFipsCode(final int fipsCode) {
		this.fipsCode = fipsCode;
	}


	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
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

	@Override
	public final String toString() {
        return "County2Zip{" 
               + "zipCode =" + zipCode 
               + ", fipsCode=" + fipsCode
               + '}';
    }
}
