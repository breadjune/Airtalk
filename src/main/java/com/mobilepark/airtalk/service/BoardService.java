package com.mobilepark.airtalk.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.mobilepark.airtalk.data.FileData;
import com.mobilepark.airtalk.data.Board;
import com.mobilepark.airtalk.repository.BoardRepository;
import com.mobilepark.airtalk.repository.FileRepository;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardService {

  private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

  @Autowired
  public BoardRepository boardRepository;

  @Autowired
  public FileRepository fileRepository;
  
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

    logger.info("params : [start : "+start+"][length : "+length+"][bCode : "+bcode+"]");

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

    logger.info("params : [start : "+start+"][length : "+length+"][bcode : "+bcode+"][type : "+type+"][keyword : "+keyword+"][total_cnt : "+total_cnt+"]");

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

  /**
   * 게시글 등록
   * 
   * @return Map<String, String>
   */
  public Board create(Board board) {
    try {
      board.setRegDate(new Date());
      logger.info("create board seq : " + board.getSeq());
      board = boardRepository.save(board);
      boardRepository.flush();
      logger.info("params : [getSeq : "+board.getSeq()+"][getTitle : "+board.getTitle()+"][getWriter : "+board.getWriter()+"][getBcode : "+board.getBcode()+"]"+
                         "[getContents : "+board.getContents()+"][getRegDate : "+board.getRegDate()+"[getModDate : "+board.getModDate()+"]");


    } catch (IllegalStateException e) {
      logger.info("잘못된 인자");
      e.printStackTrace();
    }
    
    return board;
  }

  /**
   * 게시글 수정
   * 
   * @return Map<String, String>
   */
  // public Map<String, String> modify(JSONObject form) {
  //   Map<String, String> result = new HashMap<>();
  //   try {
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
  public Map<String, String> delete(Board board) {
    Map<String, String> result = new HashMap<>();
    try {
    logger.info("params : [seq : "+board.getSeq()+"][bcode : "+board.getBcode()+"]");

      boardRepository.deleteById(board.getSeq());
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

  public Map<String, String> getfileName(FileData fileData) {
    fileData = fileRepository.findBySeq(fileData.getSeq());
    Map<String, String> map = new HashMap<>();
    String fileName = "";
    try {
      fileName = fileData.getRealFileName();
    } catch(Exception e) {
      fileName = "none";
    }
    map.put("fileName", fileName);
    return map;
  }

  public String upload(int seq, MultipartFile files) throws IOException{
      FileData fileData = new FileData();
      String originalFileName = files.getOriginalFilename();
      String fileName = originalFileName.substring(0, originalFileName.lastIndexOf(".") -1);
      String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
      String newFileName = fileName + System.currentTimeMillis() + "." + extension;
      File dest = new File(System.getProperty("user.home")+"/upload/" + newFileName);
      files.transferTo(dest);

      fileData.setSeq(seq);
      fileData.setRealFileName(originalFileName);
      fileData.setNewFileName(newFileName);
      if(!fileRepository.existsById(seq)) {
        fileData.setRegDate(new Date());
      };
      fileData.setModDate(new Date());
      
      fileRepository.save(fileData);
      
      return "sucess";
  }

  public String download(int seq) {
    logger.info("seq : " + seq);
    FileData fileData = fileRepository.findBySeq(seq);
    String fullPath = System.getProperty("user.home")+"/upload/"+fileData.getNewFileName();
    logger.info("fullPath : " + fullPath);
    return fullPath;
  }

  public String getBrowser(HttpServletRequest req) {
    String header = req.getHeader("User-Agent");
    if(header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1) return "MSIE";
    else if(header.indexOf("Chrome") > -1) return "Chrome";
    else if(header.indexOf("Opera") > -1) return "Opera";
    return "Firefox";
  }

  public String getDisposition(String filename, String browser) throws UnsupportedEncodingException {
    String dispositionPrefix = "attachment;filename=";
    String encodedFilename = null;
    if(browser.equals("MSIE")) {
      encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+","%20");
    } else if(browser.equals("Firefox")) {
      encodedFilename="\""+new String(filename.getBytes("UTF-8"),"8859_1")+ "\"";
    } else if(browser.equals("Opera")) {
      encodedFilename="\""+new String(filename.getBytes("UTF-8"),"8859_1")+ "\"";
    } else if(browser.equals("Chrome")) {
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i<filename.length(); i++) {
        char c = filename.charAt(i);
        if(c > '~') {
          sb.append(URLEncoder.encode("" +c, "UTF-8"));
          } else {
              sb.append(c); 	
        } //else
      } //for
      encodedFilename = sb.toString();
      
    } //else-if
    return dispositionPrefix + encodedFilename;
    }
}
