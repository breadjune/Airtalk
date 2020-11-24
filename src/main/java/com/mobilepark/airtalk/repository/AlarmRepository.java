package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import com.mobilepark.airtalk.data.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer>, JpaSpecificationExecutor<Alarm> {
    int countByUserIdContaining(String keyword);
    int countByCodeContaining(String keyword);
    List<Alarm> findByUserId(String keyword);
    List<Alarm> findByCode(String keyword);
    // List<Alarm> findeByReservDate(String keyword);
}
