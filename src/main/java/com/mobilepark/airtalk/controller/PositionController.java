package com.mobilepark.airtalk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.data.AlarmRecv;
import com.mobilepark.airtalk.service.AlarmRecvService;
import com.mobilepark.airtalk.service.AlarmService;
import com.mobilepark.airtalk.service.FCMService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@RequestMapping(value="/restapi/position",method = RequestMethod.POST)
public class PositionController {
  
    @Autowired
    public AlarmService alarmService;  

    @Autowired
    public AlarmRecvService alarmRecvService; 

    @Autowired
    public FCMService fcmService; 

    @RequestMapping(value="/create",method = RequestMethod.POST)
    public @ResponseBody Map<String, String> create (@RequestBody JSONObject params) {

        // Alarm alarm = new Alarm();
        // AlarmRecv alarmRecv = new AlarmRecv();
        Map<String, String> map = new HashMap<>();

        try {
            System.out.println(" Position Params : " + params);
            // System.out.println("params : ["+form.get("userId").toString()+"]["+form.get("latitude").toString()+"]["+form.get("longitude").toString()+"]["+form.get("address").toString()+"]["+form.get("description").toString()+"]");
            
            // List<Alarm> alarmList = alarmService.position(Double.parseDouble(form.get("latitude").toString()), Double.parseDouble(form.get("longitude").toString()));
            // int result = 0;

            // for(int i=0; i < alarmList.size(); i++) {
            //     System.out.println("position list : " + alarmList.get(i).toString());
            //     boolean exist = alarmRecvService.recvCheck(alarmList.get(i).getSeq(), form.get("userId").toString());
            //     System.out.println(">>>>recvCheck : ["+exist+"]");
            //     if(exist) { 
            //         alarmRecv = alarmRecvService.recvInfo(alarmList.get(i).getSeq(), form.get("userId").toString());
            //         // fcmService.sendMessageTo(token, title, body);
            //     }
            // }
            map.put("err_cd", "0000");
        } catch(Exception e) {
            e.printStackTrace();
            map.put("err_cd", "-1000");
        }
    return map;

    }

}
