package com.mobilepark.airtalk.controller;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.service.LoginService;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public LoginService loginService;

    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(@RequestBody JSONObject form, HttpServletResponse res) throws Exception {
        
        Map<String, String> map = new HashMap<>();
        
        logger.info("form data : " + form.toString());

        String email = form.get("email").toString();
        String pw = form.get("password").toString();

        logger.info("email : " + email);
        logger.info("pw : " + pw);

        Admin admin = loginService.isLogin(email, pw);
        
        //logger.info("admin data : " + admin.getAdminId());

        String token = loginService.create(admin.getAdminName(), admin.getAdminId());

        logger.info("token : " + token);
        res.setHeader("authorization", token);

        map.put("name", admin.getAdminName());
        map.put("adminGroupSeq", String.valueOf(admin.getAdminGroupSeq()));
        map.put("errorCode", "0");

        return map;

    }
}