package com.mobilepark.airtalk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray; //JSON배열 사용

@Controller
@RequestMapping("/rest/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping(value = "/selectMemberListBySearchWord.json")
    public @ResponseBody String selectMemberListBySearchWord(Model model, @RequestBody String form) {

        

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
        
      
        String data = "";
        
        JSONArray req_array = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        if(searchWord.equals("") || searchWord.equals("김애선")){
            jsonObject1.put("adminId", "kas0610");
            jsonObject1.put("adminName","김애선");
            jsonObject1.put("phone","01012341234");
            jsonObject1.put("email","kas0610@mail.com");

            req_array.add(jsonObject1);
        }
        
        
        JSONObject jsonObject2 = new JSONObject();
        if(searchWord.equals("") || searchWord.equals("홍길동")){
            jsonObject2.put("adminId","kas0584");
            jsonObject2.put("adminName","홍길동");
            jsonObject2.put("phone","01012341234");
            jsonObject2.put("email","kas0584@mail.com");

            req_array.add(jsonObject2);
        }
        

        JSONObject jsonObject3 = new JSONObject();
        if(searchWord.equals("") || searchWord.equals("전지현")){
            jsonObject3.put("adminId","kas1234");
            jsonObject3.put("adminName","전지현");
            jsonObject3.put("phone","01012341234");
            jsonObject3.put("email","kas1234@mail.com");

            req_array.add(jsonObject3);
        }

        data = req_array.toString();
        
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("items", req_array);
        //data = jsonObject.toJSONString();

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


    @RequestMapping(value = "/search.json")
    public @ResponseBody String searchData( Model model){

        logger.info("MemberController");
        return "test";
    }

}
