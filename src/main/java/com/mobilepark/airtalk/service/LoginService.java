package com.mobilepark.airtalk.service;

import java.util.List;

import com.mobilepark.airtalk.data.Menu;
import com.mobilepark.airtalk.data.MenuFunc;
import com.mobilepark.airtalk.repository.MenuFuncRepository;
import com.mobilepark.airtalk.repository.MenuRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public LoginRepository loginRepository;

    public Boolean isLogin(String id, String pw) {
        logger.info("isLogin() invoked");
        return loginRepository.existsByAdminIdAndPassword(id, pw);
    }
}
