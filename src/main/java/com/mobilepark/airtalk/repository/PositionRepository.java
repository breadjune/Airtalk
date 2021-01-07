package com.mobilepark.airtalk.repository;

import java.util.List;

import com.mobilepark.airtalk.data.Position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position,String>{
    
    @Query(value = "SELECT *, (6371*ACOS(COS(RADIANS(LATITUDE))*COS(RADIANS(?2))*"+
                   "COS(RADIANS(?3)-RADIANS(LONGITUDE))+sin(RADIANS(LATITUDE))*SIN(RADIANS(?2)))) AS distance "+
                   "FROM airtalk.TBL_POSITION WHERE USER_ID = ?1 HAVING distance <= 2", nativeQuery=true)
    Position findByPositions(String userId, double latitude, double longitude);

    @Query(value = "*, (6371*ACOS(COS(RADIANS(LATITUDE))*COS(RADIANS(?2))*"+
                   "COS(RADIANS(?3)-RADIANS(LONGITUDE))+sin(RADIANS(LATITUDE))*SIN(RADIANS(?2)))) AS distance "+
                   "FROM airtalk.TBL_POSITION WHERE USER_ID = ?1 HAVING distance <= 2", nativeQuery=true)
    Position findByPositionWithPushKey(String userId, double latitude, double longitude);
}
