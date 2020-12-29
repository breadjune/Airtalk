package com.mobilepark.airtalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import com.mobilepark.airtalk.util.FCMUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/restapi/push")
public class PushController {
    private static final Logger logger = LoggerFactory.getLogger(PushController.class);

    @RequestMapping(value="/accessToken")
    public void getaccessToken () throws IOException {
        String accessToken = FCMUtil.getAccessToken();
        logger.info("ACCESS TOKEN : [" + accessToken + "]");
    }
}
