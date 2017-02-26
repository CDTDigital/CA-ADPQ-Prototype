package com.intimetec.crns.core.service.userLocation;

import java.util.Collection;
import java.util.Optional;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.models.UserLocation;

public interface UserLocationService {
	Optional<UserLocation> getById(int id);
	
	Collection<UserLocation> getByUserId(Long userId);

	Collection<UserLocation> getByZipCode(String zipCode);
	
	Collection<UserLocation> getByPlaceId(String placeId);
	
	Optional<UserLocation> getProfileLocationByUserId(long userId);

	Optional<UserLocation> getCurrentLocationByUserId(long userId);
	
	Collection<UserLocation> getByAuthToken(String authToken) throws InvalidAuthTokenException;

	UserLocation save(UserLocation userLocation);

	void delete(UserLocation userLocation);
	
	UserLocation getLocationDetails(String lat, String lng);

	UserLocation saveLocation(UserLocation userLocation, String lattitude, String longitude);
}
