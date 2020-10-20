package com.mobilepark.airtalk.service;

import java.util.List;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.repository.AdminRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public AdminRepository adminRepository;

    public Admin isLogin(String id, String pw) {
        logger.info("isLogin() invoked");
        // return adminRepository.findByAdminIdAndPassword(id, pw);
        return null;
    }

}
