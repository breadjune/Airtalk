package com.mobilepark.airtalk.controller;

import com.mobilepark.airtalk.data.Group;
import com.mobilepark.airtalk.service.AuthGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray; //JSON배열 사용
import java.util.Iterator;
import com.mobilepark.airtalk.util.DateUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/group")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public AuthGroupService authGroupService;

    // 테스트 
    // @RequestMapping(value = "/search.json")
    // public @ResponseBody String searchData( Model model){
    //     logger.info("GroupController");
    //     return "test";
    // }

    @RequestMapping(value="/search.json" )
    public @ResponseBody String search (Model model, @RequestBody String form) {
        List<String> row;
        JSONArray req_array = new JSONArray();
        String data = "";


        String searchWord = "";
        String searchType = "";
        try {

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(form);
            JSONObject jsonObj = (JSONObject) obj;

            searchWord = (String) jsonObj.get("searchWord");
            searchType = (String) jsonObj.get("searchType");

            logger.info("searchWord : " + searchWord);
            logger.info("searchType : " + searchType);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try {
            List<Group> authGroupList = authGroupService.search();
            
            
            for(Group authGroup : authGroupList) {
                if(searchWord.equals("") || searchWord.equals(authGroup.getName())){
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("authGroupSeq",authGroup.getAuthGroupSeq().toString());
                  jsonObject.put("authName",authGroup.getName());
                  jsonObject.put("desc",authGroup.getDescription());
                  jsonObject.put("regDate",DateUtil.dateToString(authGroup.getRegDate(), "yyyy-MM-dd HH:MM"));
               
                  req_array.add(jsonObject);
                }
            }
            data = req_array.toString();

        } catch(Exception e) {
            logger.error(e.getMessage());
        }
        logger.info(data);

        return data;
    }

    @RequestMapping(value = "/getMemberInfoBySeq.json")
    public @ResponseBody String getMemberInfoBySeq(Model model, @RequestBody String form) {

        logger.info("getMemberInfoBySeq");
        String data = "";
        
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("adminId", "kas0610");
            jsonObject.put("adminName", "김애선1");
            jsonObject.put("phone", "01012341234");
            jsonObject.put("email", "kas0610@mail.com");
            
            data = jsonObject.toJSONString();

            logger.info("data" + data);

        

        return data;
    }


}
