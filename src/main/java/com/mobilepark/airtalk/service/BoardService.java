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
import java.util.Set;

import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.data.Board;
import com.mobilepark.airtalk.repository.BoardRepository;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

  @Autowired
  public BoardRepository boardRepository;
  
  @Autowired
  SpecificationService<Board> specificationService;

  private Specification<Board> getSpecification(String type, String search, String bCode) {
    
    Board board = new Board();
    Set<String> likeSet = new HashSet<>();

    if(StringUtils.isNotEmpty(bCode) && StringUtils.equals(type, "bCode")) {
      board.setBCode(bCode); 
    }
    
    if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "title")) {
      board.setTitle(search);     
    } else if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "writer")) {
      board.setWriter(search);
    }
    likeSet.add(type);
    return specificationService.like(likeSet, board);
  }


  /**
   * 알림 게시판 검색 조회
   * 
   * @return List<Board>
   */
  public Map<String, Object> list(JSONObject form) {

    List<Board> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    String bCode = "";
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
      else if(key.equals("bCode")) bCode = form.get(key).toString();
      else {
        type = key;
        keyword = form.get(key).toString();
      }
    }

    if(type.equals("")) {
      map.put("err_cd", "-11000");
      return map;
    }

    logger.info("params : [type : "+type+"][keyword : "+keyword+"][start : "+start+"][length : "+length+"][bCode : "+bCode+"]");

    if(length != 0) {
      PageRequest pageRequest = PageRequest.of(start, length);

      try {
        Specification<Board> bCodeSpecs = this.getSpecification(type, keyword, bCode);
        Specification<Board> KeywordSpecs = this.getSpecification(type, keyword, bCode);
        list = boardRepository.findAll(bCodeSpecs.and(KeywordSpecs), pageRequest).getContent();
        map.put("result", list);
        map.put("err_cd", "0000");
        map.put("total_cnt", total_cnt);
      } catch (Exception e) {
        map.put("err_cd", "-1000");
        e.printStackTrace();
      }
    } else {
      try {
        if(type.equals("title")) list = boardRepository.findByBCodeAndTitle(bCode, keyword);
        if(type.equals("writer")) list = boardRepository.findByBCodeAndWriter(bCode, keyword);
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
   * 게시글 등록
   * 
   * @return Map<String, String>
   */
  // public Map<String, String> create(JSONObject form) {
  //   Map<String, String> result = new HashMap<>();
  //   try {
  //     Board board = this.getParameter(form, "create");
  //     board.setRegDate(new Date());
  //     logger.info("params : [userId : "+board.getBCode()+"][message : "+board.getTitle()+"][code : "+board.getWriter()+"]"+
  //                        "[latitude : "+board.getContents()+"][longitude : "+board.getRegDate()+"[bdNm : "+board.getModDate()+"]");

    
  //     boardRepository.save(board);
  //     result.put("err_cd", "0000");
  //   } catch (Exception e) {
  //     result.put("err_cd", "-1000");
  //     e.getStackTrace();
  //   }
  //   return result;
  // }

  /**
   * 게시글 수정
   * 
   * @return Map<String, String>
   */
  // public Map<String, String> modify(JSONObject form) {
  //   Map<String, String> result = new HashMap<>();
  //   try {
  //     Board board = this.getParameter(form, "modify");
  //     logger.info("params : [seq : "+board.getSeq()+"][bCode : "+board.getBCode()+"]"+
  //     "[title : "+board.getTitle()+"][contents : "+board.getContents()+"]"+
  //     "[regDate : "+board.getRegDate()+"]");
  //     boardRepository.save(board);
  //     result.put("err_cd", "0000");
  //   }catch (Exception e) {
  //     result.put("err_cd", "-1000");
  //     e.getStackTrace();
  //   }
  //   return result;
  // }

  /**
   * 게시글 삭제
   * 
   * @return Map<String, String>
   */
  // public Map<String, String> remove(JSONObject form) {
  //   Map<String, String> result = new HashMap<>();
  //   try {
  //   Board board = this.getParameter(form, "remove");
  //   logger.info("params : [seq : "+board.getSeq()+"]");

  //     boardRepository.deleteById(board.getSeq());
  //     result.put("err_cd", "0000");
  //   } catch (Exception e) {
  //     result.put("err_cd", "-1000");
  //     e.getStackTrace();
  //   }
  //   return result;
  // }

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

    if(type.equals("userId")) count = boardRepository.countByBCodeAndTitleContaining(keyword);
    else if(type.equals("code")) count = boardRepository.countByBCodeAndWriterContaining(keyword);
    // else if(type.equals("code")) count = boardRepository.countByBCodeAndRegDateContaining(keyword);
    
    logger.info("Total Count1 : ["+count+"]");

    return count;
  }

  /**
   * 알림 서비스 파라미터
   * 
   * @return Alarm
   */
  // public Board getParameter(JSONObject form, String service) {
    
  //   Board board = new Board();
  //   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");

  //   if(service.equals("modify") || service.equals("remove")) {
  //     board.setSeq(Integer.parseInt(form.get("seq").toString()));
  //     board.setBCode(form.get("seq").toString());
  //   }
    
  //   if(service.equals("modify") || service.equals("create")) {
  //     board.setMessage(form.get("message").toString());
  //     try{
  //       board.setReservDate(sdf.parse(form.get("reservDate").toString()));
  //     }catch (Exception e){
  //       e.printStackTrace();
  //     }
  //   } 
    
  //   if(service.equals("create")) {
  //     alarm.setUserId(form.get("userId").toString());
  //     alarm.setCode(form.get("code").toString());
  //     alarm.setLatitude(new BigDecimal(form.get("latitude").toString()));
  //     alarm.setLongitude(new BigDecimal(form.get("longitude").toString()));
  //     alarm.setBdNm(form.get("bdNm") != null ? form.get("bdNm").toString() : "");
  //   }
  //   return alarm;
  // }

}
