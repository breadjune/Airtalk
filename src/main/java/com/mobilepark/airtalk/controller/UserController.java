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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
@RequestMapping("/restapi/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService userService;
   
    User User = new User();

   
    //API용 호출 테스트 
    // @RequestMapping(value="/list",method = RequestMethod.POST)
    // public @ResponseBody List<UserAPI> list (Model model, @RequestBody String form) {
    //     List<UserAPI> list = userService.list();
    //     logger.info("데이터 정보 : " + list);
    //     return list;
    // }

      /****************************
     --------- 그룹 조회 ---------
     ****************************/
    @RequestMapping(value="/list",method = RequestMethod.POST)
    public @ResponseBody List<User> list (Model model, @RequestBody String form) {
        List<User> list = userService.list();
        logger.info("데이터 정보 : " + list);


        return list;
    }
    //테스트 
    // @RequestMapping(value = "/create.json" , method=RequestMethod.POST)
    // public @ResponseBody String create(@RequestBody String test){
    //     logger.info("EditorController");
    //     logger.info("데이터 정보 : " + test);
    //     User = new User();
    //     String result = "";

    //           //CREATE 정보 전달
    //         try {
    //             userService.create(test);
    //             result = "SUCCESS";
    //          } catch(Exception e) {
    //             logger.error(e.getMessage());
    //             result = "FAIL";
    //     }
    //     return result;
    // }
}
