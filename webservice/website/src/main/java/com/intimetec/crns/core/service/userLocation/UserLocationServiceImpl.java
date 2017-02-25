package com.intimetec.crns.core.service.userLocation;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.models.UserLocation;
import com.intimetec.crns.core.repository.UserLocationRepository;
import com.intimetec.crns.core.service.userdevice.UserDeviceService;

@Service
public class UserLocationServiceImpl implements UserLocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserLocationServiceImpl.class);
	@Autowired
	private UserLocationRepository userLocationRepository;
	@Autowired
	private UserDeviceService userDeviceService;

	@Override
	public Optional<UserLocation> getById(int id) {
		LOGGER.debug("Getting UserLocation={}", id);
		return Optional.ofNullable(userLocationRepository.findOne(id));
	}

	@Override
	public Collection<UserLocation> getByUserId(Long userId) {
		LOGGER.debug("Getting UserLocation by user id={}", userId);
		Collection<UserLocation> UserLocation = userLocationRepository.findOneByUserId(userId);
		return UserLocation;
	}

	@Override
	public Collection<UserLocation> getByZipCode(String zipCode) {
		LOGGER.debug("Getting UserLocations by zip Code={}", zipCode);
		Collection<UserLocation> UserLocation = userLocationRepository.getByZipCode(zipCode);
		return UserLocation;
	}

	@Override
	public Collection<UserLocation> getByPlaceId(String placeId) {
		LOGGER.debug("Getting UserLocations by Place-id={}", placeId);
		Collection<UserLocation> UserLocation = userLocationRepository.getByZipCode(placeId);
		return UserLocation;
	}

	@Override
	public UserLocation getProfileLocationByUserId(long userId) {
		LOGGER.debug("Getting User's profile location by user id={}", userId);
		for(UserLocation userLocation: getByUserId(userId)){
			if(!userLocation.isCurrentLocation()){
				return userLocation;
			}
		}
		return null;
		
	}

	@Override
	public UserLocation getCurrentLocationByUserId(long userId) {
		LOGGER.debug("Getting User's profile location by user id={}", userId);
		for(UserLocation userLocation: getByUserId(userId)){
			if(userLocation.isCurrentLocation()){
				return userLocation;
			}
		}
		return null;
	}

	@Override
	public Collection<UserLocation> getByAuthToken(String authToken) throws InvalidAuthTokenException {
		Optional<UserDevice> userDevice = userDeviceService.getByAuthToken(authToken);
		if(userDevice.isPresent()) {
			long userId = userDevice.get().getUser().getId();
			Collection<UserLocation> UserLocation = getByUserId(userId);
			return UserLocation;
		} else {
			throw new InvalidAuthTokenException("Invalid Authentication Token Supplied");
		}		
	}

	@Override
	public UserLocation save(UserLocation userLocation) {
		System.out.println("Location to save: "+userLocation);
		return userLocationRepository.save(userLocation);
	}

	@Override
	public void delete(UserLocation userLocation) {
		userLocationRepository.delete(userLocation);
	}
	
	
}
