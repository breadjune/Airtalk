package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.mobilepark.airtalk.data.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer>, JpaSpecificationExecutor<Alarm> {
    int countByUserIdContaining(String keyword);
    int countByCodeContaining(String keyword);
    List<Alarm> findByUserId(String keyword);
    List<Alarm> findByCode(String keyword);
    List<Alarm> findByLatitudeAndLongitude(double latitude, double longitude);

    @Query(value = "select * from TBL_ALARM where RESERV_DATE < str_to_date(now(),'%Y-%m-%d %H:%i')", nativeQuery=true)
    List<Alarm> alarmReservPush();
    // List<Alarm> findeByReservDate(String keyword);
}
