package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.repository.AlarmRepository;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

  private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

  @Autowired
  public AlarmRepository alarmRepository;
  
  @Autowired
  SpecificationService<Alarm> specificationService;

  private Specification<Alarm> getSpecification(String type, String search) {
    
    Alarm alarm = new Alarm();
    Set<String> likeSet = new HashSet<>();

    if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "userId"))
      alarm.setUserId(search);
    else if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "code"))
      alarm.setCode(search);     

    likeSet.add(type);
    return specificationService.like(likeSet, alarm);
  }


  /**
   * 알림 게시판 검색 조회
   * 
   * @return List<Alarm>
   */
  public List<Alarm> list(JSONObject form) {

    String type = form.get("type").toString();
    String keyword = form.get("keyword").toString();
    int start = Integer.parseInt(form.get("start").toString());
    int length = Integer.parseInt(form.get("length").toString());

    logger.info("params : [type : "+type+"][keyword : "+keyword+"][start : "+start+"][length : "+length+"]");

    PageRequest pageRequest = PageRequest.of(start, length);
    return alarmRepository.findAll(this.getSpecification(type, keyword), pageRequest).getContent();
  }

  /**
   * 알림 게시판 검색 카운트 조회
   * 
   * @return Integer
   */
  public int count(JSONObject form) {
    int count = 0;

    switch(form.get("type").toString()) {
      case "userId":
        count = alarmRepository.countByUserIdContaining(form.get("keyword").toString());
      case "code":
        count = alarmRepository.countByCodeContaining(form.get("keyword").toString());
    } 

    logger.info("Total Count : ["+count+"]");

    return count;
  }

}
