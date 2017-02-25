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
	
	UserLocation getProfileLocationByUserId(long userId);

	UserLocation getCurrentLocationByUserId(long userId);
	
	Collection<UserLocation> getByAuthToken(String authToken) throws InvalidAuthTokenException;

	UserLocation save(UserLocation userLocation);

	void delete(UserLocation userLocation);

}
