package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import com.mobilepark.airtalk.data.AlarmRecv;

@Repository
public interface AlarmRecvRepository extends JpaRepository<AlarmRecv, Integer>, JpaSpecificationExecutor<AlarmRecv> {
    List<AlarmRecv> findByAlarmSeq(int seq);
    List<AlarmRecv> findByAlarmSeqAndReceiveYn(int seq, char receiveYn);
    AlarmRecv findByAlarmSeqAndUserId(int seq, String receiverId);
    void deleteByAlarmSeqAndUserId(int seq, String userId);
    boolean existsByAlarmSeqAndUserId(int seq, String receiverId);
}