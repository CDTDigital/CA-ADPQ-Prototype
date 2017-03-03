package com.intimetec.crns.core.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.County2ZipMapping;

/**
 * @author In Time Tec
 */
@Repository("county2ZipRepository")
@Transactional
public interface County2ZipRepository extends JpaRepository<County2ZipMapping, Integer> {
    Collection<County2ZipMapping> getByFipsCode(int fipsCode);

}