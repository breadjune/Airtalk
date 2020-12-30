package com.mobilepark.airtalk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.service.AlarmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/restapi/reserve",method = RequestMethod.POST)
public class ReservController {

    @Autowired
    public AlarmService alarmService;

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> create () {

        Map<String, String> map = new HashMap<>();

        try {
            List<Alarm> list = alarmService.reservList();
            map.put("err_cd", "0000");
            map.put("count", String.valueOf(list.size()));
        } catch(Exception e) {
            e.printStackTrace();
            map.put("err_cd", "-1000");
        }
    return map;

    }
}
