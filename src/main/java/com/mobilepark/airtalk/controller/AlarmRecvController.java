package com.mobilepark.airtalk.controller;

import java.util.List;

import com.mobilepark.airtalk.data.AlarmRecv;
import com.mobilepark.airtalk.service.AlarmRecvService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restapi/alarmRecv")
public class AlarmRecvController {
    
  @Autowired
  public AlarmRecvService alarmRecvService;

  @RequestMapping(value="/list",method = RequestMethod.POST)
  public @ResponseBody List<AlarmRecv> list (Model model, @RequestBody JSONObject form) {
    return alarmRecvService.list(form);
  }
}
