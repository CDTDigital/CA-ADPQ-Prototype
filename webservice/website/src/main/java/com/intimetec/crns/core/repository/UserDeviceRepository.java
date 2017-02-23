package com.intimetec.crns.core.repository;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.UserDevice;

@Repository("userDeviceRepository")
@Transactional
public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {
	Optional<UserDevice> findByUserIdAndDeviceId(long userId, String deviceId);

	Collection<UserDevice> getByUserId(long userId);
	
	Collection<UserDevice> getByUserUserName(String userName);
	
	Optional<UserDevice> findByUserUserNameAndDeviceId(String userName, String deviceId);
	
	@Modifying
	@Query("update UserDevice dev set dev.authToken = ?1, dev.deviceType = ?2, dev.deviceToken = ?3 where dev.id = ?4")
	void setUserDeviceInfoById(String authToken, String deviceType, String deviceToken, int id);
}