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
import com.intimetec.crns.core.exceptions.InvalidLocationCoordinatesException;
import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.models.UserLocation;
import com.intimetec.crns.core.repository.UserLocationRepository;
import com.intimetec.crns.core.service.userdevice.UserDeviceService;
import com.intimetec.crns.util.Utils;

/**
 * Implementation class for {@link UserLocationService}.
 * @author In Time Tec
 */

@Service
public class UserLocationServiceImpl implements UserLocationService {

	/**
	 * To log the application messages. 
	 */
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(UserLocationServiceImpl.class);
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
	public UserLocation getLocationDetails(String lat, String lng) throws InvalidLocationCoordinatesException {
		String url  = googleApiConfig.getUrl()+"?latlng="+lat+", "+lng+"&key="+googleApiConfig.getKey();
		String postalCode = null;
		String country = null;
		String stateShortName = null;
		String stateLongName = null;
		String route = null;
		String streetNumber = null;
		String placeId = null;
		String latitude = null;
		String longitude = null;
		
		String county = null;
		String cityName = null;
		RestTemplate restTemplate= new RestTemplate();
		String result = restTemplate.getForObject(url.toString(), String.class);
		UserLocation location = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
		  JsonNode jsonData = mapper.readTree(result);
		  JsonNode addressComponent = jsonData.get("results");
		  if(addressComponent!=null && addressComponent.get(0)!=null) {
			  JsonNode postalData = addressComponent.get(0).get("address_components");
			  JsonNode geometryNode = addressComponent.get(0).get("geometry").get("location");
			  placeId = addressComponent.get(0).get("place_id").textValue();
	
			  if(postalData.isArray()){
				for(final JsonNode dataNode : postalData) {
	
					if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("country,political"))
					      country =Utils.removeParenthesis( dataNode.get("long_name").toString());
	
					if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("administrative_area_level_1,political"))
					      stateLongName = Utils.removeParenthesis( dataNode.get("long_name").toString());
	
					if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("administrative_area_level_1,political"))
					      stateShortName = Utils.removeParenthesis( dataNode.get("short_name").toString());
	
					if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("administrative_area_level_2,political"))
					      county = Utils.removeParenthesis( dataNode.get("long_name").toString());
	
					if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("route"))
					      route = Utils.removeParenthesis( dataNode.get("long_name").toString());
	
					if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("street_number"))
					      streetNumber = Utils.removeParenthesis( dataNode.get("long_name").toString());
	
					if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("locality,political"))
					      cityName = Utils.removeParenthesis( dataNode.get("long_name").toString());
	
				    if(Utils.removeParenthesis(dataNode.get("types").toString()).equalsIgnoreCase("postal_code"))
				    	postalCode = Utils.removeParenthesis( dataNode.get("long_name").toString());
				 }
			  }
			  if(geometryNode!=null){
				  latitude = geometryNode.get("lat").asText();
				  longitude = geometryNode.get("lng").asText();
			  }
			  location = new UserLocation(route, streetNumber, postalCode, cityName, latitude, longitude, placeId);
		  }
		  else {
			  throw new InvalidLocationCoordinatesException("Invalid location coordinates.");
		  }
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	  return location;
	}

	@Override
	public UserLocation saveLocation(UserLocation userLocation, String latitude, String longitude) throws InvalidLocationCoordinatesException {
		UserLocation location = getLocationDetails(latitude, longitude);
		location.setCurrentLocation(userLocation.isCurrentLocation());
		location.setId(userLocation.getId());
		location.setUserId(userLocation.getUserId());
		return save(location);
	}
}
