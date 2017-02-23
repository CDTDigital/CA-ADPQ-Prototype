package com.intimetec.crns.core.service.userdevice;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.UserDevice;
import com.intimetec.crns.core.repository.UserDeviceRepository;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeviceServiceImpl.class);
	@Autowired
	private UserDeviceRepository userDeviceRepository;

	@Override
	public Optional<UserDevice> getUserDeviceById(int id) {
		LOGGER.debug("Getting userDevice={}", id);
		return Optional.ofNullable(userDeviceRepository.findOne(id));
	}

	@Override
	public Collection<UserDevice> getUserDevicesByUserName(String userName) {
		LOGGER.debug("Getting userDevice by user name={}", userName);
		Collection<UserDevice> userDevices = userDeviceRepository.getByUserUserName(userName);
		return userDevices;
	}

	@Override
	public Collection<UserDevice> getUserDevicesByUserId(long userId) {
		LOGGER.debug("Getting userDevice by user id={}", userId);
		Collection<UserDevice> userDevice = userDeviceRepository.getByUserId(userId);
		return userDevice;
	}

	@Override
	public Optional<UserDevice> getAllUserDevicesByUserNameDeviceId(String userName, String deviceId) {
		LOGGER.debug("Getting userDevice by user name, deviceId={}", userName + ", " + deviceId);
		Optional<UserDevice> userDevice = userDeviceRepository.findByUserUserNameAndDeviceId(userName, deviceId);
		return userDevice;
	}

	@Override
	public Optional<UserDevice> getAllUserDevicesByUserIdDeviceId(long userId, String deviceId) {
		LOGGER.debug("Getting userDevice by user Id, deviceId={}", userId + ", " + deviceId);
		Optional<UserDevice> userDevice = userDeviceRepository.findByUserIdAndDeviceId(userId, deviceId);
		return userDevice;
	}

	@Override
	public UserDevice save(UserDevice userDevice) {
		long userId = userDevice.getUser().getId();
		String deviceId = userDevice.getDeviceId();
		Optional<UserDevice> userDevices = userDeviceRepository.findByUserIdAndDeviceId(userId, deviceId);
		if(userDevices.isPresent()){
			userDevice.setId(userDevices.get().getId());
		}
		return userDeviceRepository.save(userDevice);
	}

	@Override
	public void delete(UserDevice userDevice) {
		userDeviceRepository.delete(userDevice.getId());
	}
}
