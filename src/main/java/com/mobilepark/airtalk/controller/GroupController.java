package com.mobilepark.airtalk.controller;

import com.mobilepark.airtalk.data.Group;
import com.mobilepark.airtalk.service.AuthGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray; //JSON배열 사용
import com.mobilepark.airtalk.util.DateUtil;
import java.util.List;

@Controller
@RequestMapping("/admin/group")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public AuthGroupService authGroupService;
    Group Group = new Group();
    // 테스트 
    // @RequestMapping(value = "/search.json")
    // public @ResponseBody String searchData( Model model){
    //     logger.info("GroupController");
    //     return "test";
    // }

     /****************************
     --------- 그룹 조회 ---------
     ****************************/
    @RequestMapping(value="/search.json" )
    public @ResponseBody String search (Model model, @RequestBody String form) {
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
    
    /****************************
     --------- 그룹 생성 ---------
     ****************************/
    @RequestMapping(value="/create.json", method=RequestMethod.POST)
    @ResponseBody    // 중요하다
    public String create(@RequestBody String param) {
            Group = new Group();
            System.out.println("파라미터 정보" + param);
            String result = "";
     //JSON파싱
        try { 
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(param);

            System.out.println("파라미터 정보 JSON" + jObject.toString());
           
            String gname = (String)jObject.get("gname"); 
            String userGroup = (String)jObject.get("userGroup"); 
            String auth = (String)jObject.get("auth"); 
            String regDate = (String)jObject.get("regDate"); 

            System.out.println("gname: " + gname);
            System.out.println("userGroup: " + userGroup);
            System.out.println("auth: " + auth);
            System.out.println("regDate: " + regDate);

              //CREATE 정보 전달
            try {
                Group = authGroupService.create(gname, userGroup, regDate);
                result = "SUCCESS";
             } catch(Exception e) {
                logger.error(e.getMessage());
                result = "FAIL";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

     /****************************
     --------- 그룹 수정 ---------
     ****************************/
    @RequestMapping(value="/update.json", method=RequestMethod.POST)
    @ResponseBody    // 중요하다
    public String update(@RequestBody String param) {
            Group = new Group();
            System.out.println("파라미터 정보" + param);
            String result = "";
     //JSON파싱
        try { 
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(param);

            System.out.println("파라미터 정보 JSON" + jObject.toString());
           
            String authGroupSeq = (String)jObject.get("authGroupSeq"); 
            String gname = (String)jObject.get("gname"); 
            String userGroup = (String)jObject.get("userGroup"); 
            String auth = (String)jObject.get("auth"); 

            System.out.println("authGroupSeq: " + authGroupSeq);
            System.out.println("gname: " + gname);
            System.out.println("userGroup: " + userGroup);
            System.out.println("auth: " + auth);

              //UPDATE 정보 전달
            try {
                Group = authGroupService.update(Integer.parseInt(authGroupSeq),gname, userGroup ,auth);
                result = "SUCCESS";
             } catch(Exception e) {
                logger.error(e.getMessage());
                result = "FAIL";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

     /****************************
     --------- 그룹 삭제 ---------
     ****************************/
    @RequestMapping(value="/remove.json", method=RequestMethod.POST)
    @ResponseBody    // 중요하다
    public String remove(@RequestBody String param) {
            Group = new Group();
            System.out.println("파라미터 정보" + param);
            String result = "";
     //JSON파싱
        try { 
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(param);

            System.out.println("파라미터 정보 JSON" + jObject.toString());
           
            String authGroupSeq = (String)jObject.get("authGroupSeq"); 

            System.out.println("authGroupSeq: " + authGroupSeq);

              //DELETE 정보 전달
            try {
                authGroupService.remove(Integer.parseInt(authGroupSeq));
                result = "SUCCESS";
             } catch(Exception e) {
                logger.error(e.getMessage());
                result = "FAIL";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

}
