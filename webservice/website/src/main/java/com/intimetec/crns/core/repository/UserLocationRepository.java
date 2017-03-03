package com.intimetec.crns.core.repository;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.UserLocation;

/**
 * @author In Time Tec
 */
@Repository("userLocationRepository")
@Transactional
public interface UserLocationRepository extends JpaRepository<UserLocation, 
Integer> {

	Collection<UserLocation> findOneByUserId(long userId);
	
	Collection<UserLocation> getByZipCode(String zipCode);
	
	Collection<UserLocation> getByPlaceId(String placeId);
	
	Optional<UserLocation> findOneByUserIdAndCurrentLocation(long userId, 
			boolean currentLocation);
}