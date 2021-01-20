package com.mobilepark.airtalk.controller;

import java.util.HashMap;

import java.util.Map;

import com.mobilepark.airtalk.service.DialogService;

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
    public DialogService dialogService; 

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> create (@RequestBody JSONObject params) {

        Map<String, String> map = new HashMap<>();

        map = dialogService.dialogPush(params);

    return map;

    }

}
