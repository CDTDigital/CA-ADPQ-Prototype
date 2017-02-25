/**
 * 
 */
package com.intimetec.crns.core.service.location;

import com.intimetec.crns.core.models.Location;

/**
 * @author harish.vaishnav
 *
 */
public interface LocationService {

	public Location getLocationDetails(String latlng);
}
