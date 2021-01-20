package com.mobilepark.airtalk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.BoardFile;
import com.mobilepark.airtalk.data.FileData;
import com.mobilepark.airtalk.repository.BoardFileRepository;
import com.mobilepark.airtalk.repository.FileRepository;
import com.mobilepark.airtalk.util.UploadUtil;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Service
public class FileService {

  private static final Logger logger = LoggerFactory.getLogger(FileService.class);
  private static String RESULT;

  @Autowired
  public BoardFileRepository boardfileRepository;

  @Autowired
  public FileRepository fileRepository;
  
  /**
  * 파일 게시판 리스트 조회
  * @return String
  */
  public List<BoardFile> search() {
    List<BoardFile> list = boardfileRepository.findAll();
    list.forEach(s -> logger.info(s.toString()));
    return list;
  }

  public String create(MultipartHttpServletRequest req, BoardFile boardFile) {
        
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    List<String> fileNameList = upload(req, boardFile.getBoardSeq());

    logger.info("boardData : " + boardFile.getTitle());

    try {
      boardfileRepository.save(boardFile);
        RESULT = "0";
    } catch(Exception e) {
        RESULT = "-1";
        e.getStackTrace();
    }

    return RESULT;
  }

  public List<String> upload(MultipartHttpServletRequest req, int boardSeq) {
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    UploadUtil uploadUtil = new UploadUtil();
    Map<String, List<String>> map = uploadUtil.upload(req);
    
    List<String> realFileList = map.get("real");
    List<String> newFileList = map.get("new");

    logger.info("realFileList Length : " + realFileList.size());
    logger.info("newFileList Length : " + newFileList.size());

    if (realFileList.size() == newFileList.size()) {
        Date date = new Date();
      for(int i=0; i < realFileList.size(); i++) {
        FileData file = new FileData();
        file.setSeq(boardSeq);
        file.setRealFileName(realFileList.get(i));
        file.setNewFileName(newFileList.get(i));
        file.setRegDate(date);

        try {
          fileRepository.save(file);
          RESULT = "0";
        } catch(Exception e) {
          RESULT = "-1";
          e.getStackTrace();
        }
      }
    }

    return realFileList;
  } 

  public String update(JSONObject form) {
      
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    BoardFile boardFile = new BoardFile();

    boardFile.setBoardSeq(Integer.parseInt(form.get("boardSeq").toString()));
    boardFile.setTitle(form.get("title").toString());
    boardFile.setWriter(form.get("writer").toString());
    boardFile.setContents(form.get("contents").toString());
    boardFile.setFileName(form.get("fileName").toString() != null ? form.get("fileName").toString() : "");
    boardFile.setModDate(sdf.format(new Date()));
    try {
      boardfileRepository.save(boardFile);
      RESULT = "0";
    } catch(Exception e) {
      RESULT = "-1";
      e.getStackTrace();
    }
    return RESULT;
  }

  public String delete(JSONObject form) {
      
    int seq = Integer.parseInt(form.get("boardSeq").toString());
    
    try {
        fileRepository.deleteById(seq);
        RESULT = "0";
    } catch (Exception e) {
        RESULT = "-1";
        e.getStackTrace();
    }
    return RESULT;
  }
}
