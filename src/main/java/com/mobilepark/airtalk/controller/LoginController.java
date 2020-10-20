package com.mobilepark.airtalk.controller;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.service.AdminService;
import com.mobilepark.airtalk.service.LoginService;

import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(@RequestBody JSONObject form) {
        
        Map<String, String> map = new HashMap<>();
        
        String id = form.get("id").toString();
        String pw = form.get("pw").toString();

        // Admin admin = loginService.isLogin(id, pw);
        
        String errorCode = "";

        // if (admin != null) errorCode = "0";
        // else errorCode = "-1";

        // map.put("name", admin.getAdminName());
        // map.put("adminGroupSeq", admin.getAdminGroupSeq());
        // map.put("errorCode", errorCode);

        return map;

    }




}