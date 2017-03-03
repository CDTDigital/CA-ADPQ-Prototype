package com.intimetec.crns.core.service.county2zip;

import java.util.Collection;

import com.intimetec.crns.core.models.County2ZipMapping;

/**
 * @author In Time Tec
 */
public interface County2ZipService {
	Collection<County2ZipMapping> getByCountyCode(int fipsCode);
}
