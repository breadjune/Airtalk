package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import com.mobilepark.airtalk.data.User;
import com.mobilepark.airtalk.data.UserAPI;
import com.mobilepark.airtalk.repository.UserRepository;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserRepository UserRepository;

    @Autowired
    SpecificationService<User> specificationService;

    //API 전용 호출
    // public List<UserAPI> list() {
    //     List<UserAPI> list = new ArrayList<>();
    //     list = UserRepository.findAllBy();
    //     return list;
    // }

    //목록 조회
    public List<User> list() {

        List<User> list = new ArrayList<>();
        list = UserRepository.findAll();

        list.forEach(s -> logger.info(s.toString()));

        return list;
    }

    @Transactional
    public void create(String param) throws ParseException {
        User User = null;

        System.out.println("test 정보: " + param);
        JSONParser parser = new JSONParser();
        JSONObject jObject;
        jObject = (JSONObject) parser.parse(param);
        try {
            /* START */
            User = new User();
            User.setId((String)jObject.get("id"));
            User.setName((String)jObject.get("name"));
            User.setPassword((String)jObject.get("password"));
            User.setHpNo((String)jObject.get("hpNo"));
            User.setRegDate(new Date());

            User = UserRepository.save(User);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void update(String param) throws ParseException {
        User User = null;

        System.out.println("test 정보: " + param);
        JSONParser parser = new JSONParser();
        JSONObject jObject;
        jObject = (JSONObject) parser.parse(param);
        try {
            /* START */
            User = new User();
            User.setId((String)jObject.get("id"));
            User.setName((String)jObject.get("name"));
            User.setPassword((String)jObject.get("password"));
            User.setHpNo((String)jObject.get("hpNo"));
            User.setModDate(new Date());

            User = UserRepository.save(User);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void delete(String param) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jObject;

        jObject = (JSONObject) parser.parse(param);
        String id = (String)jObject.get("id");

        System.out.println("id 정보: ----------" + id);

        try {
            /* START */
            UserRepository.deleteById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Specification<User> getSpecification(String type, String search) {
    
        User user = new User();
        Set<String> likeSet = new HashSet<>();
    
        if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "id")) {
          logger.info("success service");
          user.setId(search);
          likeSet.add(type);
        }
        else{
            logger.info("success service");
            user.setName(search);
            likeSet.add(type);
        }
    
    
        return specificationService.like(likeSet, user);
      }
    
        /**
         * 검색 조회
         * 
         * @return List<Code>
         */
        public List<User> search(JSONObject form) {
    
            List<User> list = new ArrayList<>();
    
            String type = form.get("type").toString();
            String keyword = form.get("keyword").toString();
            String startString = form.get("start").toString();
            int length = Integer.parseInt(form.get("length").toString());
    
            logger.info("startString : " + startString);
    
            int start = Integer.parseInt(startString);
    
            logger.info("type : " + type);
            logger.info("keyword : " + keyword);
    
            int count = UserRepository.countByIdContaining(keyword);
    
            logger.info("count : " + count);
    
            PageRequest pageRequest = PageRequest.of(start, length);
    
            // if(type.equals("default")) {
            // list = alarmRepository.findByUserIdContaining(keyword, pageRequest);
            // }
    
            list = UserRepository.findAll(this.getSpecification(type, keyword), pageRequest).getContent();
    
            return list;
        }
    
        /**
         * 검색 카운트 조회
         * 
         * @return Integer
         */
      public int count(JSONObject form) {
        return UserRepository.countByIdContaining(form.get("keyword").toString());
      }
    
}
