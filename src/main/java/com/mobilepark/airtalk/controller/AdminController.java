package com.mobilepark.airtalk.controller;

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
@RequestMapping("/admin/adminList")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping(value = "/adminSearch")
    @ResponseBody
    public ModelAndView adminList(HttpServletRequest req) {
        ModelAndView mav = new ModelAndView("admin/adminList/adminSearch");

        List<Admin> adminList = adminService.getAdminList();

        mav.addObject("adminList", adminList);

        return mav;
    }
}
