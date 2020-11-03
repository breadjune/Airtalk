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

@Controller
@RequestMapping("/rest/admin-list")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @GetMapping(value = "/adminList")
    @ResponseBody
    public List<Admin> adminList(HttpServletRequest req) {
        List<Admin> adminList = adminService.getAdminList();

        return adminList;
    }

    @GetMapping(value = "/adminViewSearch")
    @ResponseBody
    public List<Admin> adminViewSearch(HttpServletRequest req) {
        List<Admin> adminList = adminService.getAdminInfo();

        return adminList;
    }
}
