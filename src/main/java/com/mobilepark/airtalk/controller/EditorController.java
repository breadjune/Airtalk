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
import java.util.List;

@Controller
@RequestMapping("/rest/editor")
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
}
