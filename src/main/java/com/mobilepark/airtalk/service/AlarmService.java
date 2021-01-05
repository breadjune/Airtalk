package com.mobilepark.airtalk.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.data.AlarmRecv;
import com.mobilepark.airtalk.repository.AlarmRecvRepository;
import com.mobilepark.airtalk.repository.AlarmRepository;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

  private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

  @Autowired
  public AlarmRepository alarmRepository;

  @Autowired
  public AlarmRecvRepository alarmRecvRepository;
  
  @Autowired
  SpecificationService<Alarm> specificationService;

  private Specification<Alarm> getSpecification(String type, String search) {
    
    Alarm alarm = new Alarm();
    Set<String> likeSet = new HashSet<>();

    if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "userId")) {
      alarm.setUserId(search); 
    } else if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "code")) {
      alarm.setCode(search);     
    }

    likeSet.add(type);
    return specificationService.like(likeSet, alarm);
  }


  /**
   * 알림 게시판 검색 조회
   * 
   * @return List<Alarm>
   */
  public Map<String, Object> list(JSONObject form) {

    List<Alarm> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    String type = "";
    String keyword = "";
    int start = 0;
    int length = 0;
    int total_cnt = count(form);

    Iterator<?> keys = form.keySet().iterator();
    logger.info("key : " + keys.toString());
    while(keys.hasNext()) {
      String key = keys.next().toString();
      if(key.equals("type")) type = form.get(key).toString();
      else if(key.equals("keyword")) keyword = form.get(key).toString();
      else if(key.equals("start")) start = Integer.parseInt(form.get(key).toString());
      else if(key.equals("length")) length = Integer.parseInt(form.get(key).toString());
      else {
        type = key;
        keyword = form.get(key).toString();
      }
    }

    if(type.equals("")) {
      map.put("err_cd", "-11000");
      return map;
    }

    logger.info("params : [type : "+type+"][keyword : "+keyword+"][start : "+start+"][length : "+length+"]");
    if(length != 0) {
      PageRequest pageRequest = PageRequest.of(start, length);

      try {
        Specification<Alarm> specs = this.getSpecification(type, keyword);
        list = alarmRepository.findAll(specs, pageRequest).getContent();
        map.put("result", list);
        map.put("err_cd", "0000");
        map.put("total_cnt", total_cnt);
      } catch (Exception e) {
        map.put("err_cd", "-1000");
        e.printStackTrace();
      }

    } else {
      
      try {
        if(type.equals("userId")) list = alarmRepository.findByUserId(keyword);
        if(type.equals("code")) list = alarmRepository.findByCode(keyword);
        // if(type.equals("reserv_date")) list = alarmRepository.findeByReservDate(keyword);
        map.put("result", list);
        map.put("err_cd", "0000");
        map.put("total_cnt", list.size());
      } catch (Exception e) {
        map.put("err_cd", "-1000");
        e.printStackTrace();
      }
    } 

    return map;
  }

  /**
   * 알림 등록
   * 
   * @return Map<String, String>
   */
  public Map<String, String> create(JSONObject form) {
    Map<String, String> result = new HashMap<>();
    try {
      logger.info("params : ["+form+"]");
      Alarm alarm = this.getParameter(form, "create");
      alarm.setRegDate(new Date());
      logger.info("alarm VO : [userId : "+alarm.getUserId()+"][message : "+alarm.getMessage()+"][code : "+alarm.getCode()+"]"+
                         "[latitude : "+alarm.getLatitude()+"][longitude : "+alarm.getLongitude()+"[bdNm : "+alarm.getBdNm()+"]"+
                         "[reservDate : "+alarm.getReservDate()+"]");

      Alarm data = alarmRepository.save(alarm);

      ObjectMapper mapper = new ObjectMapper();
      String jsonArray = mapper.writeValueAsString(form.get("receiver"));
      List<Map<String, String>> paramMap = new ObjectMapper().readValue(jsonArray, new TypeReference<List<Map<String, String>>>(){});
      logger.info("alarm recv List params : " + paramMap.toString());
      logger.info("alarm create seq : " + data.getSeq());
      // List<AlarmRecv> recvList = new ArrayList<>();
      for(int i=0; i < paramMap.size(); i++) {
        AlarmRecv alarmRecv = new AlarmRecv();
        alarmRecv.setAlarmSeq(data.getSeq());
        alarmRecv.setUserId(paramMap.get(i).get("userId"));
        alarmRecv.setHpNo(paramMap.get(i).get("hpNo"));
        alarmRecv.setReceiveYn('N');
        alarmRecv.setRegDate(new Date());
        
        // recvList.add(alarmRecv);
        alarmRecvRepository.save(alarmRecv);
      }
      // for(int i=0; i < recvList.size(); i++) {
      //   logger.info("alarm recv List VO : " + recvList.get(i).toString());
      // }
      // alarm.setAlarmRecvs(recvList);
      
      // List<Alarm> info = new ArrayList<>();
      // Optional<Alarm> info = alarmRepository.findById(5);
      // for(int i=0; i < info.size(); i++) {
        // logger.info("alarm List VO : " + info.get().getAlarmRecvs().get(0).toString());
      // }
      result.put("err_cd", "0000");
    } catch (Exception e) {
      result.put("err_cd", "-1000");
      e.getStackTrace();
    }
    return result;
  }

  /**
   * 알림 수정
   * 
   * @return Map<String, String>
   */
  public Map<String, String> modify(JSONObject form) {
    Map<String, String> result = new HashMap<>();
    try {
      Alarm alarm = this.getParameter(form, "modify");
      logger.info("params : [seq : "+alarm.getSeq()+"][message : "+alarm.getMessage()+"]"+
      "[reservDate : "+alarm.getReservDate()+"]");
      alarmRepository.save(alarm);
      result.put("err_cd", "0000");
    }catch (Exception e) {
      result.put("err_cd", "-1000");
      e.getStackTrace();
    }
    return result;
  }

  /**
   * 알림 삭제
   * 
   * @return Map<String, String>
   */
  public Map<String, String> remove(JSONObject form) {
    Map<String, String> result = new HashMap<>();
    try {
    Alarm alarm = this.getParameter(form, "remove");
    logger.info("params : [seq : "+alarm.getSeq()+"]");

      alarmRepository.deleteById(alarm.getSeq());
      result.put("err_cd", "0000");
    } catch (Exception e) {
      result.put("err_cd", "-1000");
      e.getStackTrace();
    }
    return result;
  }

  /**
   * 알림 게시판 검색 카운트 조회
   * 
   * @return Integer
   */
  public int count(JSONObject form) {
    int count = 0;
    String type = "";
    String keyword = "";
    Iterator<?> keys = form.keySet().iterator();
    while(keys.hasNext()) {
      String key = keys.next().toString();
      if(key.equals("type")) type= form.get(key).toString();
      if(key.equals("keyword")) keyword = form.get(key).toString();
    }

    if(type.equals("userId")) count = alarmRepository.countByUserIdContaining(keyword);
    else if(type.equals("code")) count = alarmRepository.countByCodeContaining(keyword);

    logger.info("Total Count1 : ["+count+"]");

    return count;
  }

  /**
   * 알림 서비스 파라미터
   * 
   * @return Alarm
   */
  public Alarm getParameter(JSONObject form, String service) {
    
    Alarm alarm = new Alarm();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");

    if(service.equals("modify") || service.equals("remove")) {
      alarm.setSeq(Integer.parseInt(form.get("seq").toString()));
    }
    
    if(service.equals("modify") || service.equals("create")) {
      alarm.setMessage(form.get("message").toString());
      try{
      alarm.setReservDate(sdf.parse(form.get("reservDate").toString()));
      }catch (Exception e){
        e.printStackTrace();
      }
    } 
    
    if(service.equals("create")) {
      alarm.setUserId(form.get("userId").toString());
      alarm.setCode(form.get("code").toString());
      alarm.setLatitude(Double.parseDouble(form.get("latitude").toString()));
      alarm.setLongitude(Double.parseDouble(form.get("longitude").toString()));
      alarm.setBdNm(form.get("bdNm") != null ? form.get("bdNm").toString() : "");
    }
    return alarm;
  }

  /**
   * 알림 서비스 파라미터
   * 
   * @return List<Alarm>
   */
  public List<Alarm> position(double latitude, double longitude) {
    
    List<Alarm> list = alarmRepository.findByLatitudeAndLongitude(latitude, longitude);
    
    return list;
  }

  /**
   * 알림 예약 리스트 조회
   * 
   * @return List<Alarm>
   */
  public List<Alarm> reservList() {
    List<Alarm> list = alarmRepository.alarmReservPush();
    logger.info("reserv list size : " + list.size());
    return list;
  }
}
