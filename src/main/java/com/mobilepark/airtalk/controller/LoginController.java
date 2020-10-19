package com.mobilepark.airtalk.controller;

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

    @RequestMapping("/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(@RequestBody JSONObject form) {
        
        Map<String, String> map = new HashMap<>();
        
        String id = form.get("id").toString();
        String pw = form.get("pw").toString();

        boolean check = loginService.isLogin(id, pw);

        if (check != 0) String errorState = -1;
        else String errorState = 0;

        map.put("uid", id);
        map.put("errorState", errorState);
        map.put("isAuth", check);

        return map;

    }




}