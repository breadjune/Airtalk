package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import com.mobilepark.airtalk.data.User;
import com.mobilepark.airtalk.repository.UserRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String create(String param) throws ParseException {
        User User = null;

        System.out.println("test 정보: " + param);
        JSONParser parser = new JSONParser();
        JSONObject jObject;
        jObject = (JSONObject) parser.parse(param);

        if(UserRepository.findByUserId((String)jObject.get("id"))==null){
            try {
             /* START */
                User = new User();
                User.setUserId((String)jObject.get("id"));
                User.setName((String)jObject.get("name"));
                User.setPassword((String)jObject.get("password"));
                User.setHpNo((String)jObject.get("hpNo"));
                User.setPushKey((String)jObject.get("pushKey"));
                User.setRegDate(new Date());

                User = UserRepository.save(User);
                return "SUCC";
            } catch (Exception e) {
                logger.error(e.getMessage());
                return e.getMessage();
            }
        } 
        else{
            return "FAIL";
        }
    }

    @Transactional
    public String view(String param) throws ParseException {
        System.out.println("test 정보: " + param);
        User User = null;
        JSONParser parser = new JSONParser();
        JSONObject jObject;

        jObject = (JSONObject) parser.parse(param);
        User= UserRepository.findByUserId((String)jObject.get("id"));

        String result = ToStringBuilder.reflectionToString(User, ToStringStyle.JSON_STYLE);
        System.out.println("User 정보11:-------- " + ToStringBuilder.reflectionToString(User, ToStringStyle.JSON_STYLE));
        
        return result;
    }

    @Transactional
    public int update(String param) throws ParseException {
        User User = null;

        System.out.println("test 정보: " + param);
        JSONParser parser = new JSONParser();
        JSONObject jObject;
        jObject = (JSONObject) parser.parse(param);
            if(UserRepository.findByPasswordAndUserId((String)jObject.get("bunpassword"),(String)jObject.get("id") )!=null) { 
                //비밀번호 체크
                 try {
                     /* START */
               
                    //  UserRepository.findByUserId((String)jObject.get("id"));
                     User = new User();
                     User.setUserId((String)jObject.get("id"));
                     User.setName((String)jObject.get("name"));
                     if (String.valueOf(jObject.get("password")).equals(""))
                          User.setPassword((String)jObject.get("bunpassword"));
                     else
                         User.setPassword((String)jObject.get("password"));
                        
                    User.setHpNo((String)jObject.get("hpNo"));
                    User.setPushKey((String)jObject.get("pushKey"));
                    User.setModDate(new Date());
    
                    User = UserRepository.save(User);
                    return 0;
    
            } catch (Exception e) {
                logger.error(e.getMessage());
                return 1;
            }
        } else 
            return 3; 
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
    
        if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "userId")) {
          logger.info("success service");
          user.setUserId(search);
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
            int length = Integer.parseInt(form.get("length").toString());
            int start = Integer.parseInt(form.get("start").toString());

            PageRequest pageRequest = PageRequest.of(start, length ,Sort.by("regDate"));
            if (type == "all")
                list = UserRepository.findAll(pageRequest).getContent();
            else
                list = UserRepository.findAll(this.getSpecification(type, keyword), pageRequest).getContent();
    
            return list;
        }
    
        /**
         * 검색 카운트 조회
         * 
         * @return Integer
         */
      public int count(JSONObject form) {
        int count = 0;
        switch(form.get("type").toString()) {
          case "all":
            count = UserRepository.countByAll();
            break;
          case "userId":
            count = UserRepository.countByUserIdContaining(form.get("keyword").toString());
            break;
          case "name":
            count = UserRepository.countByNameContaining(form.get("keyword").toString());
            break;
        } 
        logger.info("Total Count : ["+count+"]");
        return count;

      }
    
}
