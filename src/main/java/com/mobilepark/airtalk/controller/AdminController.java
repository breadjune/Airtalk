package com.mobilepark.airtalk.controller;

import java.util.HashMap;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.service.AdminService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/rest/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminService adminService;

    @GetMapping(value = "/search.json")
    @ResponseBody
    public List<Admin> search(HttpServletRequest req) {
        List<Admin> adminList = adminService.search();

        return adminList;
    }

    @GetMapping(value = "/view.json")
    @ResponseBody
    public Admin view(HttpServletRequest req) {
        String adminId = req.getQueryString().substring(8, req.getQueryString().length());

        Admin adminInfo = adminService.view(adminId);
        
        return adminInfo;
    }

    @PostMapping(value = "/update.json")
    public @ResponseBody HashMap<String,String> update(@RequestBody String param) {

        System.out.println("데이터 정보 : " + param);
        int resultCode = 0;
        HashMap<String,String> result = new HashMap<String,String>();

           //CREATE 정보 전달
           try {
            resultCode = adminService.update(param);
                result.put("result", "SUCCESS");
                result.put("resultCode", "0");
            } catch(Exception e) {
                logger.error(e.getMessage());
                result.put("result", "FAIL");
                result.put("resultCode", "-1");
            }  
            if(resultCode ==1) {
             result.put("result", "FAIL");
             result.put("resultCode", "-1");
             }
            else if(resultCode ==3) {
                result.put("result", "PASSFAIL");
                result.put("resultCode", "-3");
            }
   
         System.out.println("리턴값 : " + result.toString());
        return result;

    }

    @PostMapping(value = "/create.json")
    @ResponseBody
    public String create(Admin admin) {

        System.out.println("getAdminId : " + admin.getAdminId());
        System.out.println("getAdminName : " + admin.getAdminName());
        System.out.println("getAdminGroupSeq : " + admin.getAdminGroupSeq());
        System.out.println("getPassword : " + admin.getPassword());
        System.out.println("getPhone : " + admin.getPhone());
        System.out.println("getEmail : " + admin.getEmail());

        String result = adminService.create(admin);

        return result;
    }

    @GetMapping(value = "/delete.json")
    @ResponseBody
    public String delete(String adminId) {
        String result = "";

        result = adminService.delete(adminId);

        return result;
    }
    
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public @ResponseBody List<Admin> search (Model model, @RequestBody JSONObject form, Pageable pageable) {
      return adminService.search(form);
  
    }
    @RequestMapping(value="/count",method = RequestMethod.POST)
    public @ResponseBody int count (Model model, @RequestBody JSONObject form) { 
        
        return adminService.count(form);
  }

}
