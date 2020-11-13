package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "default")) {
      alarm.setUserId(search);
      likeSet.add(type);
    }

    return specificationService.like(likeSet, alarm);
  }


  /**
   * 알림 게시판 검색 조회
   * 
   * @return List<Alarm>
   */
  public List<Alarm> list(JSONObject form) {

    List<Alarm>list = new ArrayList<>();

    String type = form.get("type").toString();
    String keyword = form.get("keyword").toString();
    String startString = form.get("start").toString();

    logger.info("startString : " + startString);

    int start = Integer.parseInt(startString);

    logger.info("type : " + type);
    logger.info("keyword : " + keyword);

    int count = alarmRepository.countByUserIdContaining(keyword);

    logger.info("count : " + count);

    PageRequest pageRequest = PageRequest.of(start/7, 7);

    // if(type.equals("default")) {
    //   list = alarmRepository.findByUserIdContaining(keyword, pageRequest);
    // }
        

    list = alarmRepository.findAll(this.getSpecification(type, keyword), pageRequest).getContent();

    return list;
  }

}
