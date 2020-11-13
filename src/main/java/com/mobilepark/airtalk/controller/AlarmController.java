package com.mobilepark.airtalk.controller;

import java.util.List;

import com.mobilepark.airtalk.data.Alarm;
import com.mobilepark.airtalk.service.AlarmService;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restapi/alarm")
public class AlarmController {
  private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);
  
  @Autowired
  public AlarmService alarmService;

  @RequestMapping(value="/list",method = RequestMethod.POST)
  public @ResponseBody List<Alarm> search (Model model, @RequestBody JSONObject form, Pageable pageable) {
    List<Alarm> list = alarmService.list(form);

    return list;

  }
}
