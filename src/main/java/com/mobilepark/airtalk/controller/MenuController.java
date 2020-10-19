package com.mobilepark.airtalk.controller;

import java.util.List;

import com.mobilepark.airtalk.data.Menu;
import com.mobilepark.airtalk.service.MenuService;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MenuService menuService;
    
    @RequestMapping("/gettest")
    @ResponseBody
    public String gettest(@RequestParam(value="test1", required = false) String test1 , @RequestParam(value = "test2", required = false) String test2) {
        logger.info("get - id : " + test1 + " pwd : " + test2);
        return "getsucc";
    }

    @RequestMapping(value = "/posttest", method = RequestMethod.POST)
    @ResponseBody
    public int posttest( @RequestBody JSONObject body){
        String id = body.get("id").toString();
        String pwd = body.get("pwd").toString();

        List<Menu> m = menuService.getMenu();
        for(int i = 0; i<m.size(); i++){
            System.out.println("seq : " + m.get(i).getMenuSeq());
        }

        logger.info("post - id : " + id + " pwd : " + pwd);
        if(id.equals("test") && pwd.equals("123")){
            return 1;
        }else
            return 0;
    }

    
}
