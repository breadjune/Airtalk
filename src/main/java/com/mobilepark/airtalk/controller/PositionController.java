package com.mobilepark.airtalk.controller;

import java.util.HashMap;
import java.util.Map;

import com.mobilepark.airtalk.service.FCMService;
import com.mobilepark.airtalk.service.PositionService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/restapi/position",method = RequestMethod.POST)
public class PositionController {

    @Autowired
    public FCMService fcmService; 

    @Autowired
    public PositionService positionService; 

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> create (@RequestBody JSONObject params) {

        Map<String, String> map = new HashMap<>();

        System.out.println(" Position Create Params : " + params);
        map = positionService.create(params);

    return map;

    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> update (@RequestBody JSONObject params) {

        Map<String, String> map = new HashMap<>();

        System.out.println(" Position Update Params : " + params);
        map = positionService.update(params);

    return map;

    }

}
