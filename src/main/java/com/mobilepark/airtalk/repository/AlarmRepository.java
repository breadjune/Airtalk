package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.data.AlarmRecv;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer>, JpaSpecificationExecutor<Alarm> {
    int countByUserIdContaining(String keyword);
    int countByCodeContaining(String keyword);
    List<Alarm> findByUserId(String keyword);
    List<Alarm> findByCode(String keyword);
    List<Alarm> findByLatitudeAndLongitude(double latitude, double longitude);
    // @Query(value = "select * from "+
    //                "TBL_ALARM_RECEIVER left join TBL_ALARM on TBL_ALARM_RECEIVER.ALARM_SEQ = TBL_ALARM.SEQ "+
    //                "where latitude=:latitude and longitude=:longitude and user_id=:user_id")
    // List<AlarmRecv> alarmRecv(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("user_id") String user_id);
    // List<Alarm> findeByReservDate(String keyword);
}
