package com.mobilepark.airtalk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.service.AdminService;

import org.hibernate.annotations.SourceType;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/admin")
public class AdminController {

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
    @ResponseBody
    public String update(Admin admin) {

        System.out.println("getAdminGroupSeq : " + admin.getAdminGroupSeq());
        System.out.println("getPhone : " + admin.getPhone());
        System.out.println("getEmail : " + admin.getEmail());
        System.out.println("getPassword : " + admin.getPassword());

        String result = adminService.update(admin);

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
