package com.mobilepark.airtalk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.mobilepark.airtalk.data.AlarmRecv;
import com.mobilepark.airtalk.repository.AlarmRecvRepository;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AlarmRecvService {

  private static final Logger logger = LoggerFactory.getLogger(AlarmRecvService.class);

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
    logger.info("AlarmRecvService invoked!");
    List<AlarmRecv> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
  
    int alarmSeq = 0;
    String userId = "";
    char receiveYn = 'N';
    Date receiveDate = null;
    
    Iterator<?> keys = form.keySet().iterator();
    logger.info("keys : " + form.keySet().toString());
    while(keys.hasNext()) {
      String key = keys.next().toString();
      
      if(key.equals("alarmSeq")) alarmSeq = Integer.parseInt(form.get("alarmSeq").toString());
      else if(key.equals("userId")) userId = form.get("userId").toString();
      else if(key.equals("receiveYn")) receiveYn = form.get("receiveYn").toString().charAt(0);
      else {
        map.put("err_cd", "-11000");
        return map;
      }
    }

    logger.info("param : ["+alarmSeq+"]");
    
    try{
      list = alarmRecvRepository.findByAlarmSeq(alarmSeq);
      map.put("result", list);
      map.put("err_cd", "0000");
      map.put("total_cnt", list.size());
    } catch (Exception e) {
      map.put("err_cd", "-1000");
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
    try{
      AlarmRecv alarmRecv = this.getParameter(form, "create");
      logger.info("params : [alarmSeq : "+alarmRecv.getAlarmSeq()+"][userId : "+alarmRecv.getUserId()+"][hpNo : "+alarmRecv.getHpNo()+"]"+
                          "[receiveYn : "+alarmRecv.getReceiveYn()+"[regDate : "+alarmRecv.getRegDate()+"]");
      
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
    try{
      AlarmRecv alarmRecv = this.getParameter(form, "modify");
      logger.info("params : [alarmSeq : "+alarmRecv.getAlarmSeq()+"][userId : "+alarmRecv.getUserId()+"][hpNo : "+alarmRecv.getHpNo()+"]"+
                          "[receiveYn : "+alarmRecv.getReceiveYn()+"[regDate : "+alarmRecv.getRegDate()+"]");
    
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
  @Transactional
  public Map<String, String> remove(JSONObject form) {
    Map<String, String> result = new HashMap<>();
    try{
      int seq = Integer.parseInt(form.get("alarmSeq").toString());
      String userId = form.get("userId").toString();
      logger.info("param : ["+seq+"]["+userId+"]");
    
      alarmRecvRepository.deleteByAlarmSeqAndUserId(seq, userId);
      result.put("err_cd", "0000 ");
    } catch (Exception e) {
      result.put("err_cd", "-1000 ");
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
        alarmRecv.setAlarmSeq(Integer.parseInt(form.get("alarmSeq").toString()));
        alarmRecv.setUserId(form.get("userId").toString());
      }

      if(service.equals("create")) {
        alarmRecv.setHpNo(form.get("hpNo").toString());
        alarmRecv.setReceiveYn('N');
        alarmRecv.setRegDate(new Date());
        alarmRecv.setModDate(new Date());
      }

      if(service.equals("modify")) {
        alarmRecv.setHpNo(form.get("hpNo").toString());
        alarmRecv.setReceiveYn(form.get("receiveYn") != null ? form.get("receiveYn").toString().charAt(0) : 'N');
        alarmRecv.setModDate(new Date());
        alarmRecv.setReceiveDate(new Date());
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return alarmRecv;
  }
}
