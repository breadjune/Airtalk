package com.mobilepark.airtalk.controller;

import java.util.Map;

import com.mobilepark.airtalk.service.PhoneBookService;

import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/restapi/phoneBook",method = RequestMethod.POST)
public class PhoneBookController {
  
    @Autowired
    public PhoneBookService phoneBookService;  

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> create (@RequestBody JSONObject params) {
        Map<String, String> map = phoneBookService.create(params);
        return map;
    }

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> list (@RequestBody JSONObject params) {
        Map<String, Object> map = phoneBookService.list(params.get("userId").toString());
        return map;
    }
}
