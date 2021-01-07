package com.mobilepark.airtalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mobilepark.airtalk.service.FCMService;
import com.mobilepark.airtalk.util.FCMUtil;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/restapi/push")
public class PushController {
    private static final Logger logger = LoggerFactory.getLogger(PushController.class);

    @Autowired
    public FCMService fcmService;  

    @RequestMapping(value="/accessToken")
    public void getaccessToken() throws IOException {
        String accessToken = FCMUtil.getAccessToken();
        logger.info("ACCESS TOKEN : [" + accessToken + "]");
    }

    @RequestMapping(value="/message")
    public @ResponseBody Map<String, String> message(@RequestBody JSONObject params) throws IOException {
        Map<String, String> map = new HashMap<>();
        logger.info("PARAMS : [" + params+ "]");
        // String pushKey = "cO2_unv92BI:APA91bFW_SjGedLSq1C6Az87B2BfXJPpo99knmiGiMVyhZgQDrB3zb3UWTfvXezDzyIrxKUUsf5pq5vZTrgzdo3gzvgau_6bLgYoZqfPYD_wO6qq9p5H_QNG9ijMBL3qqEA29yIIKlxS";
        try {
            fcmService.sendMessageTo(params.get("pushKey").toString(), params.get("title").toString(), params.get("body").toString(), 
                                     params.get("sender").toString(), params.get("receiver").toString());
            map.put("result", "0000");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "-1000");
        }
        return map;
    }
}
