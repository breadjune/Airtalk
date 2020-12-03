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

  private Specification<Board> getSpecification(String bcode) {
    Board board = new Board();
    Set<String> likeSet = new HashSet<>();
    board.setBcode(bcode); 
    likeSet.add("bcode");
    return specificationService.like(likeSet, board);

  }

  private Specification<Board> getSpecificationSearch(String type, String search) {
    Board board = new Board();
    Set<String> likeSet = new HashSet<>();
    if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "title"))
      board.setTitle(search);
    else if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "writer"))
      board.setWriter(search);

    likeSet.add(type);
    return specificationService.like(likeSet, board);
  }

  /**
   * 알림 게시판 조회
   * 
   * @return Map<String, List<Board>>
   */
  public Map<String, Object> list(JSONObject form) {

    List<Board> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    String bcode = form.get("bcode").toString();
    int start = Integer.parseInt(form.get("start").toString());
    int length = Integer.parseInt(form.get("length").toString());
    int total_cnt = listCount(bcode);

    PageRequest pageRequest = PageRequest.of(start, length);
    Specification<Board> bCodeSpecs = this.getSpecification(bcode);

    try {
      list = boardRepository.findAll(bCodeSpecs, pageRequest).getContent();
      map.put("result", list);
      map.put("err_cd", "0000");
      map.put("total_cnt", total_cnt);
    } catch (Exception e) {
      map.put("err_cd", "-1000");
      e.printStackTrace();
    }

    logger.info("params : [start : "+start+"][length : "+length+"][bCode : "+bcode+"]");
    
    return map;
  }

  /**
   * 알림 게시판 검색 조회
   * 
   * @return Map<String, List<Board>>
   */
  public Map<String, Object> search(JSONObject form) {
    List<Board> list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    String bcode = form.get("bcode").toString();
    String type = form.get("type").toString();
    String keyword = form.get("keyword").toString();
    int start = Integer.parseInt(form.get("start").toString());
    int length = Integer.parseInt(form.get("length").toString());
    int total_cnt = searchCount(bcode, type, keyword);

    PageRequest pageRequest = PageRequest.of(start, length);
    Specification<Board> bCodeSpecs = this.getSpecification(bcode);
    Specification<Board> searchSpecs = this.getSpecificationSearch(type, keyword);

    try {
      list = boardRepository.findAll(bCodeSpecs.and(searchSpecs), pageRequest).getContent();
      map.put("result", list);
      map.put("err_cd", "0000");
      map.put("total_cnt", total_cnt);
    } catch (Exception e) {
      map.put("err_cd", "-1000");
      e.printStackTrace();
    }

    return map;
  }

  //   if(length != 0) {
  //     try {
  //       // Specification<Board> bCodeSpecs = this.getSpecification(type, keyword, bcode);
  //       Specification<Board> KeywordSpecs = this.getSpecificationSearch(type, keyword);
  //       list = boardRepository.findAll(bCodeSpecs.and(KeywordSpecs), pageRequest).getContent();
  //       map.put("result", list);
  //       map.put("err_cd", "0000");
  //       map.put("total_cnt", total_cnt);
  //     } catch (Exception e) {
  //       map.put("err_cd", "-1000");
  //       e.printStackTrace();
  //     }
  //   } else {
  //     try {
  //       if(type.equals("title")) list = boardRepository.findByBcodeAndTitle(bcode, keyword);
  //       if(type.equals("writer")) list = boardRepository.findByBcodeAndWriter(bcode, keyword);
  //       map.put("result", list);
  //       map.put("err_cd", "0000");
  //       map.put("total_cnt", list.size());
  //     } catch (Exception e) {
  //       map.put("err_cd", "-1000");
  //       e.printStackTrace();
  //     }
  //   } 

  //   return map;
  // }

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
  public int listCount(String bcode) {
    int count = boardRepository.countByBcode(bcode);
    logger.info("list count : ["+count+"]");
    return count;
  }

  public int searchCount(String bcode, String type, String keyword) {
    int count = 0;
    if(type.equals("title")) count = boardRepository.countByBcodeAndTitleContaining(bcode, keyword);
    else if(type.equals("writer")) count = boardRepository.countByBcodeAndWriterContaining(bcode, keyword);

    logger.info("search count : ["+count+"]");

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
