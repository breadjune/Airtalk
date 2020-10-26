package com.mobilepark.airtalk.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rest/admin-list")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping(value = "/adminSearch")
    @ResponseBody
    public List<Admin> adminList(HttpServletRequest req) {
        List<Admin> adminList = adminService.getAdminList();

        // ModelAndView mav = new ModelAndView("adminList");

        // for(int i = 0; i < adminList.size(); i++) {
        //     System.out.println("--------- adminList : " + adminList.get(i));
        // }

        // mav.addObject("adminList", adminList);

        return adminList;
    }
}
