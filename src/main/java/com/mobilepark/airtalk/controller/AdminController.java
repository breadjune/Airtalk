package com.mobilepark.airtalk.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/admin-list")
public class AdminController {

    @GetMapping(value = "/getTest")
    @ResponseBody
    public String getTest(HttpServletRequest req) {
        String test = req.getParameter("return getTest");
        return test;
    }

    @PostMapping(value = "/postTest")
    @ResponseBody
    public String postTest(@RequestBody JSONObject object) {
        String result = "";

        String id = object.get("id").toString();
        String pw = object.get("pw").toString();

        if (id.equals("sss424") && pw.equals("abc123")) {
            result = "Login Success";
        }
        else {
            result = "Login Fail";
        }

        return result;
    }
}
