
package com.mobilepark.airtalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilepark.airtalk.data.Code;
import com.mobilepark.airtalk.service.CodeService;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/restapi/svcCode")
public class CodeController {
    private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    public CodeService codeService;
   
    Code Code = new Code();

      /****************************
         --------- 조회 ---------
     ****************************/
    @RequestMapping(value="/list",method = RequestMethod.POST)
    public @ResponseBody List<Code> list () {
        List<Code> list = codeService.list();
        logger.info("데이터 정보 : " + list.toString());


        return list;
    }
    /****************************
        --------- 생성 ---------
     ****************************/
    @RequestMapping(value = "/create" , method=RequestMethod.POST)
    public @ResponseBody  HashMap<String,String> create(@RequestBody String param){
        logger.info("데이터 정보 : " + param);
        Code = new Code();
        HashMap<String,String> result = new HashMap<String,String>();

        //CREATE 정보 전달
            try {
                codeService.create(param);
                result.put("result", "SUCCESS");
                result.put("resultCode", "0");
             } catch(Exception e) {
                result.put("result", "FAIL");
                result.put("resultCode", "-1");
        }
        return result;
    }

    /****************************
       ---------삭제 ---------
     ****************************/
    @RequestMapping(value="/remove", method=RequestMethod.POST)
    @ResponseBody    // 중요하다
    public HashMap<String,String> delete(@RequestBody String param) {
        Code = new Code();
        logger.info("데이터 정보 : " + param);
        HashMap<String,String> result = new HashMap<String,String>();

            try {
                codeService.delete(param);
                result.put("result", "SUCCESS");
                result.put("resultCode", "0");
             } catch(Exception e) {
                logger.error(e.getMessage());
                result.put("result", "FAIL");
                result.put("resultCode", "-1");
            }

        return result;
    }

    @RequestMapping(value="/search",method = RequestMethod.POST)
    public @ResponseBody List<Code> search (Model model, @RequestBody JSONObject form, Pageable pageable) {
      return codeService.search(form);
  
    }
    @RequestMapping(value="/count",method = RequestMethod.POST)
    public @ResponseBody int count (Model model, @RequestBody JSONObject form) { 
        
        return codeService.count(form);
  }

}
