package com.mobilepark.airtalk.controller;

import java.util.HashMap;

import java.util.Map;

import com.mobilepark.airtalk.service.AlarmRecvService;
import com.mobilepark.airtalk.service.AlarmService;
import com.mobilepark.airtalk.service.FCMService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@RequestMapping(value="/restapi/dialog",method = RequestMethod.POST)
public class DialogController {
  
    @Autowired
    public AlarmService alarmService;  

    @Autowired
    public AlarmRecvService alarmRecvService; 

    @Autowired
    public FCMService fcmService; 

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> create (@RequestBody JSONObject params) {

        Map<String, Object> map = new HashMap<>();

        try {
            System.out.println("params : " + params);
            
            map.put("err_cd", "0000");
        } catch(Exception e) {
            e.printStackTrace();
            map.put("err_cd", "-1000");
        }
    return map;

    }

}
