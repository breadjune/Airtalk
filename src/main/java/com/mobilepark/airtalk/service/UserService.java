package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.mobilepark.airtalk.data.User;
import com.mobilepark.airtalk.data.UserAPI;
import com.mobilepark.airtalk.repository.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserRepository UserRepository;

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

    // @Transactional
    // public void create(String test) throws ParseException {
    //     User User = null;

    //     System.out.println("test 정보: " + test);
    //     JSONParser parser = new JSONParser();
    //     JSONObject jObject;
        
    //         jObject = (JSONObject) parser.parse(test);
    //     try {
    //         /* START */
    //         User = new User();
    //         User.setAdminId((String)jObject.get("adminId"));
    //         User.setTitle((String)jObject.get("title"));
    //         User.setContents((String)jObject.get("content"));
    //         User.setRegDate(new Date());

    //         User = UserRepository.save(User);

    //     } catch (Exception e) {
    //         logger.error(e.getMessage());
    //     }
    // }
}
