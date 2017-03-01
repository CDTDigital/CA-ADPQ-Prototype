package com.intimetec.crns.core.service.userdevice;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.intimetec.crns.core.models.UserDevice;

public interface UserDeviceService {
	Optional<UserDevice> getUserDeviceById(int id);

	Collection<UserDevice> getUserDevicesByUserName(String userName);

	Optional<UserDevice> getAllUserDevicesByUserNameDeviceId(String userName, String deviceId);

	Collection<UserDevice> getUserDevicesByUserId(long userId);
	
	Collection<UserDevice> getUserDevicesByUserIds(List<Long> userIdList);

	Optional<UserDevice> getAllUserDevicesByUserIdDeviceId(long userId, String deviceId);
	
	Optional<UserDevice> getByAuthToken(String authToken);

	UserDevice save(UserDevice userDevice);

	void delete(UserDevice userDevice);

}
