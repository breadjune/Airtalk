package com.mobilepark.airtalk.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.AlarmRecv;
import com.mobilepark.airtalk.repository.AlarmRecvRepository;


import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AlarmRecvService {

  private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

  @Autowired
  public AlarmRecvRepository alarmRecvRepository;

  @Autowired
  SpecificationService<AlarmRecv> specificationService;

  /**
   * 알림 수신자 조회
   * 
   * @return List<AlarmRecv>
   */
  public Map<String, Object> list(JSONObject form) {
    List<AlarmRecv> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    int alarmSeq = Integer.parseInt(form.get("alarm_seq").toString());
    logger.info("param : ["+alarmSeq+"]");
    
    try{
      list = alarmRecvRepository.findByAlarmSeq(alarmSeq);
      map.put("err_cd", "0000");
      map.put("result", list);
      map.put("total_cnt", list.size());
    } catch (Exception e) {
      map.put("err_cd", "-1000");
      map.put("result", "");
      map.put("total_cnt", "0");
      e.printStackTrace();
    }
    
    return map;
  }

  /**
   * 알림 수신자 등록
   * 
   * @return List<AlarmRecv>
   */
  public Map<String, String> create(JSONObject form) {
    Map<String, String> result = new HashMap<>();
    AlarmRecv alarmRecv = this.getParameter(form, "create");
    logger.info("params : [alarmSeq : "+alarmRecv.getAlarmSeq()+"][userId : "+alarmRecv.getUserId()+"][hpNo : "+alarmRecv.getHpNo()+"]"+
                         "[receiveYn : "+alarmRecv.getReceiveYn()+"[regDate : "+alarmRecv.getRegDate()+"]");
    try{
      alarmRecvRepository.save(alarmRecv);
      result.put("err_cd", "0000");
    } catch (Exception e) {
      result.put("err_cd", "-1000");
      e.printStackTrace();
    }

    return result;
  }

  /**
   * 알림 수신자 수정
   * 
   * @return List<AlarmRecv>
   */
  public Map<String, String> modify(JSONObject form) {
    Map<String, String> result = new HashMap<>();
    AlarmRecv alarmRecv = this.getParameter(form, "modify");
    logger.info("params : [alarmSeq : "+alarmRecv.getAlarmSeq()+"][userId : "+alarmRecv.getUserId()+"][hpNo : "+alarmRecv.getHpNo()+"]"+
                         "[receiveYn : "+alarmRecv.getReceiveYn()+"[regDate : "+alarmRecv.getRegDate()+"]");
    try{
      alarmRecvRepository.save(alarmRecv);
      result.put("err_cd", "0000");
    } catch (Exception e) {
      result.put("err_cd", "-1000");
      e.printStackTrace();
    }

    return result;
  }

  /**
   * 알림 수신자 삭제
   * 
   * @return List<AlarmRecv>
   */
  public Map<String, String> remove(JSONObject form) {
    Map<String, String> result = new HashMap<>();
    int seq = Integer.parseInt(form.get("alarmSeq").toString());
    String userId = form.get("userId").toString();
    logger.info("param : ["+seq+"]");
    try{
      alarmRecvRepository.deleteByAlarmSeqAndUserId(seq, userId);
      result.put("err_cd", "0000");
    } catch (Exception e) {
      result.put("err_cd", "-1000");
      e.printStackTrace();
    }

    return result;
  }

  /**
   * 수신자 VO 세팅
   * 
   * @return AlarmRecv
   */
  public AlarmRecv getParameter(JSONObject form, String service) {
    AlarmRecv alarmRecv = new AlarmRecv();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    try {
      if(service.equals("create") || service.equals("modify") || service.equals("remove")) {
        alarmRecv.setAlarmSeq(Integer.parseInt(form.get("alarm_seq").toString()));
        alarmRecv.setUserId(form.get("user_id").toString());
      }

      if(service.equals("create")) {
        alarmRecv.setHpNo(form.get("hp_no").toString());
        alarmRecv.setReceiveYn('N');
        alarmRecv.setRegDate(new Date());
      }

      if(service.equals("modify")) {
        alarmRecv.setHpNo(form.get("hp_no").toString());
        alarmRecv.setReceiveYn(form.get("receive_yn") != null ? form.get("receive_yn").toString().charAt(0) : 'N');
        alarmRecv.setModDate(new Date());
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return alarmRecv;
  }
}
