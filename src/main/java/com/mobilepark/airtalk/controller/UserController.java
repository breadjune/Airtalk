package com.mobilepark.airtalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilepark.airtalk.data.User;
import com.mobilepark.airtalk.data.UserAPI;
import com.mobilepark.airtalk.service.UserService;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

@Controller
@RequestMapping("/restapi/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService userService;

    User User = new User();

    // API용 호출 테스트
    // @RequestMapping(value="/list",method = RequestMethod.POST)
    // public @ResponseBody List<UserAPI> list (Model model, @RequestBody String
    // form) {
    // List<UserAPI> list = userService.list();
    // logger.info("데이터 정보 : " + list);
    // return list;
    // }

    /****************************
     * --------- 조회 ---------
     ****************************/
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public @ResponseBody List<User> list() {
        List<User> list = userService.list();
        logger.info("데이터 정보 : " + list.toString());

        return list;
    }

    /****************************
     * --------- 생성 ---------
     ****************************/
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, String> create(@RequestBody String param) {
        logger.info("데이터 정보 : " + param);
        User = new User();

        HashMap<String, String> result = new HashMap<String, String>();

        // CREATE 정보 전달
        try {
            userService.create(param);
            result.put("result", "SUCCESS");
            result.put("resultCode", "0");
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.put("result", "FAIL");
            result.put("resultCode", "-1");
        }
        return result;
    }

    /****************************
     * --------- 상세 정보 ---------
     ****************************/
    @RequestMapping(value = "modify", method = RequestMethod.GET)
    public @ResponseBody String modify(@RequestBody String param){
        logger.info("데이터 정보 : " + param);
        HashMap<String,String> result = new HashMap<String,String>();
        String resultMod = new String();
        
         //CREATE 정보 전달
         try {
            resultMod = userService.modify(param);
         } catch(Exception e) {
            logger.error(e.getMessage());
            result.put("result", "FAIL");
            result.put("resultCode", "-1");
            resultMod = result.toString();
    }
    return resultMod;
    }

    /****************************
        --------- 수정 ---------
     ****************************/
    @RequestMapping(value = "modify" , method=RequestMethod.POST)
    public @ResponseBody HashMap<String,String> update(@RequestBody String param){
        logger.info("데이터 정보 : " + param);
        User = new User();
        int resultCode = 0;
        HashMap<String,String> result = new HashMap<String,String>();

        //CREATE 정보 전달
            try {
                resultCode = userService.update(param);
                result.put("result", "SUCCESS");
                result.put("resultCode", "0");
             } catch(Exception e) {
                logger.error(e.getMessage());
                result.put("result", "FAIL");
                result.put("resultCode", "-1");
           if(resultCode ==1) {
            result.put("result", "FAIL");
            result.put("resultCode", "-1");
           }

        }
        return result;
    }

    /****************************
       ---------삭제 ---------
     ****************************/
    @RequestMapping(value="/remove", method=RequestMethod.POST)
    @ResponseBody    // 중요하다
    public HashMap<String,String> delete(@RequestBody String param) {
        User = new User();
        logger.info("데이터 정보 : " + param);
        HashMap<String,String> result = new HashMap<String,String>();

         //JSON파싱
            try {
                userService.delete(param);
                result.put("result", "SUCCESS");
                result.put("resultCode", "0");
             } catch(Exception e) {
                logger.error(e.getMessage());
                result.put("result", "FAIL");
                result.put("resultCode", "-1");
            }

        return result;
    }

    @RequestMapping(value="/search",method = RequestMethod.POST)
    public @ResponseBody List<User> search (Model model, @RequestBody JSONObject form, Pageable pageable) {
      return userService.search(form);
  
    }
    @RequestMapping(value="/count",method = RequestMethod.POST)
    public @ResponseBody int count (Model model, @RequestBody JSONObject form) { 
        
        return userService.count(form);
  }

}
