package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.Comment;
import com.mobilepark.airtalk.repository.CommentRepository;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentRepository commentRepository;

    public Map<String, Object> list(int seq) {

      List<Comment> list = new ArrayList<>();
      Map<String, Object> map = new HashMap<>();
  
      try {
        list = commentRepository.findBySeqOrderByRegDateDesc(seq);
        logger.info("list size : " + list.size());
        logger.info("list : " + list.get(0).toString());
        map.put("result", list);
        map.put("err_cd", "0000");
        map.put("total_cnt", list.size());
      } catch (Exception e) {
        map.put("err_cd", "-1000");
        e.printStackTrace();
      }
        
      return map;
    }

    public Map<String, Object> create(JSONObject form) {

      Map<String, Object> map = new HashMap<>();
      Comment comment = new Comment();

      try {
        comment.setAdminId(form.get("adminId").toString());
        comment.setSeq(Integer.parseInt(form.get("seq").toString()));
        comment.setComment(form.get("comment").toString());
        comment.setRegDate(new Date());
        comment.setModDate(new Date());

        commentRepository.save(comment);
        map.put("err_cd", "0000");
      } catch (Exception e) {
        e.printStackTrace();      
        map.put("err_cd", "-1000");  
      }

      return map;
    }
}
