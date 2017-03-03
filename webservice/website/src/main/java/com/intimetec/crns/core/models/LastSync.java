package com.intimetec.crns.core.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for LastSync table.
 * @author In Time Tec
 */
@Entity
@Table(name = "last_sync")
public class LastSync {
	/**
	 * Id of the User.
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	/**
	 * noaa_last_sync_time
	 */
	@Column(name = "noaa_last_sync_time")
	private Date noaaLastSyncTime;

	/**
	 * @return Noaa Last Sync Time
	 */
	public final Date getNoaaLastSyncTime() {
		return noaaLastSyncTime;
	}

	/**
	 * @param noaaLastSyncTime 
	 */	
	public final void setNoaaLastSyncTime(final Date noaaLastSyncTime) {
		this.noaaLastSyncTime = noaaLastSyncTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public final String toString() {
        return "User{" 
               + "noaaLastSyncTime =" + noaaLastSyncTime 
               + '}';
    }
}
