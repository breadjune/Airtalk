package com.mobilepark.airtalk.service;

import com.mobilepark.airtalk.common.TokenProvider;
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

    @Autowired
    public TokenProvider tokenProvider;

    // properties에서 비밀키 가지고 옴
    // @Value("${spring.jwt.token.secret-key}") String secretKey;
    
    // Login check
    public Admin isLogin(String email, String pw) {
        logger.info("isLogin() invoked");
        return adminRepository.findByAdminIdAndPassword(email, pw);
    }

    public String create(String name, String uid) throws Exception {
        String token = tokenProvider.createToken(name, uid);
        // tokenProvider.tokenReader(token);
        return token;
    }

} //loginService end 
