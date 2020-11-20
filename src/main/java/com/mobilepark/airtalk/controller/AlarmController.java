package com.mobilepark.airtalk.controller;

import java.util.List;
import java.util.Map;

import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.service.AlarmService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restapi/alarm")
public class AlarmController {
  
  @Autowired
  public AlarmService alarmService;

  @RequestMapping(value="/list",method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> list (Model model, @RequestBody JSONObject form) {
    return alarmService.list(form);
  }

  @RequestMapping(value="/create",method = RequestMethod.POST)
  public @ResponseBody Map<String, String> create (Model model, @RequestBody JSONObject form) {
    return alarmService.create(form);
  }

  @RequestMapping(value="/modify",method = RequestMethod.POST)
  public @ResponseBody Map<String, String> modify (Model model, @RequestBody JSONObject form) {
    return alarmService.modify(form);
  }

  @RequestMapping(value="/remove",method = RequestMethod.POST)
  public @ResponseBody Map<String, String> remove (Model model, @RequestBody JSONObject form) {
    return alarmService.remove(form);
  }

  @RequestMapping(value="/count",method = RequestMethod.POST)
  public @ResponseBody int count (Model model, @RequestBody JSONObject form) { 
    return alarmService.count(form);
  }
}
