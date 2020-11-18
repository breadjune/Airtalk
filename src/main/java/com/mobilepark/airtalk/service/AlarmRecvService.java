package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.midi.Sequence;

import com.mobilepark.airtalk.data.AlarmRecv;
import com.mobilepark.airtalk.repository.AlarmRecvRepository;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AlarmRecvService {

  private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

  @Autowired
  public AlarmRecvRepository alarmRecvRepository;

  @Autowired
  SpecificationService<AlarmRecv> specificationService;

  /**
   * 알림 상세 게시판 조회
   * 
   * @return List<AlarmRecv>
   */
  public List<AlarmRecv> list(JSONObject form) {
    int seq = Integer.parseInt(form.get("seq").toString());
    logger.info("param : ["+seq+"]");
    return alarmRecvRepository.findByAlarmSeq(seq);
  }
}
