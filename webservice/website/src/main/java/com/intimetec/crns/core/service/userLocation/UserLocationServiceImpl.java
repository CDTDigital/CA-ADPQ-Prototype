package com.intimetec.crns.core.service.userLocation;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.core.config.google.GoogleApiConfig;
import com.intimetec.crns.core.exceptions.InvalidAuthTokenException;
import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.models.UserLocation;
import com.intimetec.crns.core.repository.UserLocationRepository;
import com.intimetec.crns.core.service.userdevice.UserDeviceService;
import com.intimetec.crns.util.GenericService;

@Service
public class UserLocationServiceImpl implements UserLocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserLocationServiceImpl.class);
	@Autowired
	private UserLocationRepository userLocationRepository;
	@Autowired
	private UserDeviceService userDeviceService;
	@Autowired
	private GoogleApiConfig googleApiConfig;

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
	public Optional<UserLocation> getProfileLocationByUserId(long userId) {
		LOGGER.debug("Getting User's profile location by user id={}", userId);
		return userLocationRepository.findOneByUserIdAndCurrentLocation(userId, false);
		
	}

	@Override
	public Optional<UserLocation> getCurrentLocationByUserId(long userId) {
		return userLocationRepository.findOneByUserIdAndCurrentLocation(userId, true);
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
	
	/**
	 * This service is used for fetching location details on the behalf of Latitude and Longitude.
	 * @param: latlng
	 */
	@Override
	public UserLocation getLocationDetails(String lat, String lng) {
		String url  = googleApiConfig.getUrl()+"?latlng="+lat+","+lng+"&key="+googleApiConfig.getKey();
		String postalCode = null;
		String country = null;
		String stateShortName = null;
		String stateLongName = null;
		String route = null;
		String streetNumber = null;
		String placeId = null;
		
		String county = null;
		String cityName = null;
		RestTemplate restTemplate= new RestTemplate();
		String result = restTemplate.getForObject(url.toString(), String.class);
		UserLocation location = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
		  JsonNode jsonData = mapper.readTree(result);
		  JsonNode addressComponent = jsonData.get("results");
		  JsonNode postalData = addressComponent.get(0).get("address_components");
		  GenericService utilService=new GenericService();

		  if(postalData.isArray()){
			for(final JsonNode userNode : postalData) {

				if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("country,political"))
				      country =utilService.removeParenthesis( userNode.get("long_name").toString());

				if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("administrative_area_level_1,political"))
				      stateLongName = utilService.removeParenthesis( userNode.get("long_name").toString());

				if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("administrative_area_level_1,political"))
				      stateShortName = utilService.removeParenthesis( userNode.get("short_name").toString());

				if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("administrative_area_level_2,political"))
				      county = utilService.removeParenthesis( userNode.get("long_name").toString());

				if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("route"))
				      route = utilService.removeParenthesis( userNode.get("long_name").toString());

				if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("street_number"))
				      streetNumber = utilService.removeParenthesis( userNode.get("long_name").toString());

				if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("locality,political"))
				      cityName = utilService.removeParenthesis( userNode.get("long_name").toString());

			    if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("postal_code"))
			    	postalCode = utilService.removeParenthesis( userNode.get("long_name").toString());
			    
			    if(utilService.removeParenthesis(userNode.get("types").toString()).equalsIgnoreCase("street_address"))
			    	placeId = utilService.removeParenthesis( userNode.get("place_id").toString());
			 }
		  }
		  location = new UserLocation(stateShortName, county, route, streetNumber, postalCode, cityName, lat, lng, placeId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	  return location;
	}
}
