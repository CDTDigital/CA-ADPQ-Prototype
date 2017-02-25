/**
 * 
 */
package com.intimetec.crns.core.service.location;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.crns.core.config.google.GoogleApiConfig;
import com.intimetec.crns.core.models.Location;
import com.intimetec.crns.util.GenericService;

/**
 * @author harish.vaishnav
 *
 */
@Service
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private GoogleApiConfig googleApiConfig;

	/**
	 * This service is used for fetching location details on the behalf of Latitude and Longitude.
	 * @param: latlng
	 */
	@Override
	public Location getLocationDetails(String latlng) {
		String url  = googleApiConfig.getUrl()+"?latlng="+latlng+"&key="+googleApiConfig.getKey();
		String postal = null;
		String country = null;
		String stateShortName = null;
		String stateLongName = null;
		String route = null;
		String streetNumber = null;
		Location postalCode = null;
		String county = null;
		String cityName = null;
		RestTemplate restTemplate= new RestTemplate();
		String result = restTemplate.getForObject(url.toString(), String.class);
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
			      postal = utilService.removeParenthesis( userNode.get("long_name").toString());
			 }
		  }
		  postalCode = new Location(country,stateLongName,stateShortName,county,route, streetNumber,postal, cityName);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	  return postalCode;
	}

}
