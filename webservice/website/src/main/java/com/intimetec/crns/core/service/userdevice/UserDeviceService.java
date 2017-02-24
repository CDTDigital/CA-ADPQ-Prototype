package com.intimetec.crns.core.service.userdevice;

import java.util.Collection;
import java.util.Optional;

import com.intimetec.crns.core.models.UserDevice;

public interface UserDeviceService {
	Optional<UserDevice> getUserDeviceById(int id);

	Collection<UserDevice> getUserDevicesByUserName(String userName);

	Optional<UserDevice> getAllUserDevicesByUserNameDeviceId(String userName, String deviceId);

	Collection<UserDevice> getUserDevicesByUserId(long userId);

	Optional<UserDevice> getAllUserDevicesByUserIdDeviceId(long userId, String deviceId);
	
	Optional<UserDevice> getByAuthToken(String authToken);

	UserDevice save(UserDevice userDevice);

	void delete(UserDevice userDevice);

}
