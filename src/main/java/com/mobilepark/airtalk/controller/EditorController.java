package com.mobilepark.airtalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilepark.airtalk.data.Editor;
import com.mobilepark.airtalk.service.EditorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/restapi/editor")
public class EditorController {
    private static final Logger logger = LoggerFactory.getLogger(EditorController.class);

    @Autowired
    public EditorService editorService;
   
    Editor Editor = new Editor();

     /****************************
     --------- 그룹 조회 ---------
     ****************************/

    @RequestMapping(value="/search.json",method = RequestMethod.POST)
    public @ResponseBody List<Editor> search (Model model, @RequestBody String form) {
        List<Editor> list = editorService.search();
        logger.info("데이터 정보 : " + list);

        return list;
    }
    //테스트 
    @RequestMapping(value = "/create.json" , method=RequestMethod.POST)
    public @ResponseBody String create(@RequestBody String test){
        logger.info("EditorController");
        logger.info("데이터 정보 : " + test);
        Editor = new Editor();
        String result = "";

              //CREATE 정보 전달
            try {
                editorService.create(test);
                result = "SUCCESS";
             } catch(Exception e) {
                logger.error(e.getMessage());
                result = "FAIL";
        }
        return result;
    }

      /****************************
        --------- 수정 ---------
     ****************************/
    @RequestMapping(value = "modify" , method=RequestMethod.POST)
    public @ResponseBody HashMap<String,String> update(@RequestBody String param){
        logger.info("데이터 정보 : " + param);
        Editor = new Editor();
        int resultCode = 0;
        HashMap<String,String> result = new HashMap<String,String>();

        //CREATE 정보 전달
            try {
                resultCode = editorService.update(param);
                result.put("result", "SUCCESS");
                result.put("resultCode", "0");
             } catch(Exception e) {
                logger.error(e.getMessage());
                result.put("result", "FAIL");
                result.put("resultCode", "-1");
           if(resultCode ==1) {
            result.put("result", "FAIL");
            result.put("resultCode", "-1");
           }

        }
        return result;
    }

    /****************************
       ---------삭제 ---------
     ****************************/
    @RequestMapping(value="/remove", method=RequestMethod.POST)
    @ResponseBody    // 중요하다
    public HashMap<String,String> delete(@RequestBody String param) {
        Editor = new Editor();
        logger.info("데이터 정보 : " + param);
        HashMap<String,String> result = new HashMap<String,String>();

         //JSON파싱
            try {
                editorService.delete(param);
                result.put("result", "SUCCESS");
                result.put("resultCode", "0");
             } catch(Exception e) {
                logger.error(e.getMessage());
                result.put("result", "FAIL");
                result.put("resultCode", "-1");
            }

        return result;
    }
}
