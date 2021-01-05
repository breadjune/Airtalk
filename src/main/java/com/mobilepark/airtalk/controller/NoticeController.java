package com.mobilepark.airtalk.controller;

import java.util.List;

import com.mobilepark.airtalk.data.Notice;
import com.mobilepark.airtalk.service.NoticeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/notice")
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    public NoticeService noticeService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public List<Notice> List(@RequestParam(value="type", required = false) String type, @RequestParam(value="search", required = false) String search){
        logger.info("Notice_List_api");
        System.out.println("search : " + search + " / type : " + type);
        if(StringUtils.isEmpty(search) || StringUtils.isEmpty(type)){
            System.out.println("search : '', type : ''");
            List<Notice> a = noticeService.getAllList();
            System.out.println("aaaaaaaaa");
            return a;
        }else{
            System.out.println("else");
            return null;
        }
    }
    @RequestMapping(value = "/getnotice", method = RequestMethod.GET)
    @ResponseBody
    public Notice getNotice(@RequestParam(value="seq") String seq){
        int id = Integer.parseInt(seq);
        System.out.println("Notice_getNotice() id : " + id);
        return noticeService.getNotice(id);
    }
}