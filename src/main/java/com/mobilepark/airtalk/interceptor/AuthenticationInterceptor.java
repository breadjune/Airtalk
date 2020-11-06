package com.mobilepark.airtalk.interceptor;

import com.mobilepark.airtalk.common.TokenProvider;
import com.mobilepark.airtalk.service.LoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    // @Autowired
    // public TokenProvider tp;

    @Value("${spring.jwt.token.secret-key}") String secretKey;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean expire = false;
        
        TokenProvider tp = new TokenProvider();

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                String name = cookie.getName();
                String token = cookie.getValue();
                logger.info("auth name : " + name);
                logger.info("auth cookie : " + token);
                if(cookie.getName().equals("auth")) {
                    // tp.tokenReader(token);
                    expire = tp.validationToken(token);
                    logger.info("expire check : " + expire);
                    if(!expire) response.setHeader("authcheck", "false");
                }
            }
        } else {
            response.setHeader("authcheck", "false");
            logger.info("expire check : " + expire);
        }
        return true;
    }
}