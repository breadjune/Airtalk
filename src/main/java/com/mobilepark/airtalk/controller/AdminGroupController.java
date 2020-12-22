package com.mobilepark.airtalk.controller;

import com.mobilepark.airtalk.data.AdminGroup;
import com.mobilepark.airtalk.data.Menu;
import com.mobilepark.airtalk.data.AdminGroupAuth;
import com.mobilepark.airtalk.service.AdminGroupService;
import com.mobilepark.airtalk.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray; //JSON배열 사용
import com.mobilepark.airtalk.util.DateUtil;

import java.util.List;


@Controller
@RequestMapping("/rest/group")
public class AdminGroupController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGroupController.class);

    @Autowired
    public AdminGroupService authGroupService;
   
    @Autowired
    public MenuService menuService;

    AdminGroup AdminGroup = new AdminGroup();
    
    // 테스트 
    // @RequestMapping(value = "/search.json")
    // public @ResponseBody String searchData( Model model){
    //     logger.info("GroupController");
    //     return "test";
    // }

     /****************************
     --------- 그룹 조회 ---------
     ****************************/
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/search.json" )
    public @ResponseBody String search (Model model, @RequestBody String form) {
        JSONArray req_array = new JSONArray();
        String data = "";
        //검색이 필요하다면 사용
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
            e.printStackTrace();
        }
        try {
            List<AdminGroup> authGroupList = authGroupService.search();
            
            for(AdminGroup authGroup : authGroupList) {
                if(searchWord.equals("") || searchWord.equals(authGroup.getName())){
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("authGroupSeq",authGroup.getAdminGroupSeq().toString());
                  jsonObject.put("name",authGroup.getName());
                  jsonObject.put("description",authGroup.getDescription());
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
     --------- 메뉴 불러오기 화면 ----- 
     ****************************/
    @RequestMapping(value="/menuList.json", method=RequestMethod.GET)
    @ResponseBody
    public String menuList() {
        List<Menu> menuAuthList = null;
        String data = "";
        JSONArray req_array = new JSONArray();
        
        try {
            menuAuthList = menuService.getMenu();
            
            for(Menu menu : menuAuthList) {
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("menuSeq",menu.getMenuSeq().toString());
                  jsonObject.put("title",menu.getTitle());
              
                  req_array.add(jsonObject);
            }
            data = req_array.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info(data);
       
        return data;
    }

    /****************************
     --------- 그룹 상세 화면 ----- 
     ****************************/
    @RequestMapping(value="/view.json", method=RequestMethod.GET)
    @ResponseBody
    public String view(@RequestParam("adminGroupSeq") Integer adminGroupSeq ) {
        List<AdminGroupAuth> adminGroupAuthList = null;
        String data = "";
        JSONArray req_array = new JSONArray();
        try {
            adminGroupAuthList = authGroupService.getMenuAuthList(adminGroupSeq);
            
            for(AdminGroupAuth adminGroupAuth : adminGroupAuthList) {
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("auth",adminGroupAuth.getAuth());
                  req_array.add(jsonObject);
            }
            data = req_array.toString();
        } catch (Exception e) {
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
        AdminGroup = new AdminGroup();
            String result = "";
     //JSON파싱
        try { 
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(param);

            //String gname = (String)jObject.get("gname"); 
            String gname = String.valueOf(jObject.get("gname")); 
            String userGroup = (String)jObject.get("userGroup"); 
            Object auth = jObject.get("auth");
            Object menuSeq = jObject.get("menuSeq");

              //CREATE 정보 전달
            try {
                AdminGroup = authGroupService.create(gname, userGroup, auth.toString() , menuSeq.toString());
                result = "SUCCESS";
             } catch(Exception e) {
                logger.error(e.getMessage());
                result = "FAIL";
            }
        } catch (ParseException e) {
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
        AdminGroup = new AdminGroup();
            String result = "";
     //JSON파싱
        try { 
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(param);
            System.out.println(" authGroupSeq : " + String.valueOf(jObject.get("authGroupSeq")));
            System.out.println(" gname : " + (String)jObject.get("gname"));
            System.out.println(" userGroup : " + (String)jObject.get("userGroup"));


            String authGroupSeq = String.valueOf(jObject.get("authGroupSeq")); 
            String gname = (String)jObject.get("gname"); 
            String userGroup = (String)jObject.get("userGroup"); 
            Object auth = jObject.get("auth");
            Object menuSeq = jObject.get("menuSeq");


           

              //UPDATE 정보 전달
            try {
                AdminGroup = authGroupService.update(Integer.parseInt(authGroupSeq),gname, userGroup ,auth.toString(), menuSeq.toString());
                result = "SUCCESS";
             } catch(Exception e) {
                logger.error(e.getMessage());
                result = "FAIL";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

     /****************************
     --------- 그룹 삭제 ---------
     ****************************/
    @RequestMapping(value="/delete.json", method=RequestMethod.POST)
    @ResponseBody    // 중요하다
    public String delete(@RequestBody String param) {
        AdminGroup = new AdminGroup();
            String result = "";
     //JSON파싱
        try { 
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(param);
            String authGroupSeq = String.valueOf(jObject.get("authGroupSeq")); 
              //DELETE 정보 전달
            try {
                authGroupService.delete(Integer.parseInt(authGroupSeq));
                result = "SUCCESS";
             } catch(Exception e) {
                logger.error(e.getMessage());
                result = "FAIL";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/search",method = RequestMethod.POST)
    public @ResponseBody List<AdminGroup> search (Model model, @RequestBody JSONObject form, Pageable pageable) {
      return authGroupService.search(form);
  
    }
    @RequestMapping(value="/count",method = RequestMethod.POST)
    public @ResponseBody int count (Model model, @RequestBody JSONObject form) { 
        
        return authGroupService.count(form);
  }

}
