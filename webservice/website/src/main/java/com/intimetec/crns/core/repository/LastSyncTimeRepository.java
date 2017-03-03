package com.intimetec.crns.core.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intimetec.crns.core.models.LastSync;

/**
 * @author In Time Tec
 */
@Repository("lastSyncTimeRepository")
@Transactional
public interface LastSyncTimeRepository extends JpaRepository<LastSync, Integer> {
    
    @Query(value = "Select * from Last_Sync ls Order By ls.noaa_last_sync_time DESC Limit 1", nativeQuery=true)
    Optional<LastSync> findLatestNoaaSyncTime();
}