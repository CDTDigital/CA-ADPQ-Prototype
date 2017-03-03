package com.intimetec.crns.core.service.county2zip;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.County2ZipMapping;
import com.intimetec.crns.core.repository.County2ZipRepository;

/**
 * @author In Time Tec
 */
@Service
public class County2ZipServiceImpl implements County2ZipService {

	/**
	 * To log the application messages.
	 */
    private static final Logger LOGGER = 
    		LoggerFactory.getLogger(County2ZipServiceImpl.class);
    
    /**
	 * Instance of the class {@link County2ZipRepository}.
	 */
    @Autowired
    private County2ZipRepository county2ZipRepository;

    /**
	 * Get Zip codes by conty fips code
	 * @param id   the fips code
	 * @return     the zip codes of that county.
	 */
    @Override
    public Collection<County2ZipMapping> getByCountyCode(int fipsCode) {
        LOGGER.debug("Getting County2ZipMapping={}", fipsCode);
        return county2ZipRepository.getByFipsCode(fipsCode);
    }
}
