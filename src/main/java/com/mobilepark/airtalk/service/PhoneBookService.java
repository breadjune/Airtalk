package com.mobilepark.airtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilepark.airtalk.data.PhoneBook;
import com.mobilepark.airtalk.repository.PhoneBookRepository;
import com.mobilepark.airtalk.repository.UserRepository;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PhoneBookService {
    
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookService.class);

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PhoneBookRepository phoneBookRepository;

    public Map<String, String> create(JSONObject params) {

        Map<String, String> map = new HashMap<>();
        int count = 0;

        try {
            logger.info(" PhoneBook Create Params : " + params);
            logger.info("Array : " + params.get("data"));
            ObjectMapper mapper = new ObjectMapper();
            String jsonArray = mapper.writeValueAsString(params.get("data"));
            List<Map<String, String>> paramMap = new ObjectMapper().readValue(jsonArray, new TypeReference<List<Map<String, String>>>(){});
            String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
            int memberCount = 0;
            int notMemberCount = 0;

            count = phoneBookRepository.deleteByUserId(params.get("userId").toString());
            logger.info("데이터 삭제 개수 : " + count);
            logger.info("연락처 개수 : " + paramMap.size());
            for(int i=0; i < paramMap.size(); i++) {
               logger.info("paramMap list : " + paramMap.get(i).toString());
               if(userRepository.existsById(paramMap.get(i).get("phoneNumber").replaceAll(match, ""))) {
                PhoneBook phoneBook = new PhoneBook();
                phoneBook.setUserId(params.get("userId").toString());
                phoneBook.setName(paramMap.get(i).get("name"));
                phoneBook.setPhoneNumber(paramMap.get(i).get("phoneNumber"));
                phoneBook.setRegDate(new Date());
                phoneBook.setModDate(new Date());
 
                phoneBookRepository.save(phoneBook);
                memberCount += 1;
               } else {
                   logger.info("Not AirTalk member");
                   notMemberCount += 1;
               }
            }
            map.put("err_cd", "0000");
        } catch(Exception e) {
            e.printStackTrace();
            map.put("err_cd", "-1000");
        }

        return map;
    }

    public Map<String, Object> list(String userId) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        try {
            logger.info("PhoneBook List params : " + userId);
            List<PhoneBook> list = phoneBookRepository.findByUserId(userId);
            for(int i=0; i < list.size(); i++) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", list.get(i).getName().toString());
                data.put("phoneNumber", list.get(i).getPhoneNumber().toString());
                dataList.add(data);
            }
            map.put("err_cd", "0000");
            map.put("user_id", userId);
            map.put("count", list.size());
            map.put("result", dataList);
        } catch(Exception e) {
            map.put("err_cd", "-1000");
            e.printStackTrace();
        }

        return map;
    }
}
