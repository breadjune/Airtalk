package com.mobilepark.airtalk.interceptor;

import com.mobilepark.airtalk.common.TokenProvider;
import com.mobilepark.airtalk.data.AdminGroupAuth;
import com.mobilepark.airtalk.data.MenuFunc;
// import com.mobilepark.airtalk.data.SessionAttrName;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean expire = false;

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                logger.info("auth cookie : " + cookie.getValue());
                if("auth".equals(cookie)) {
                    tokenProvider.tokenReader(cookie.toString());
                    expire = tokenProvider.validationToken(cookie.toString());

                }
            }
        }
        return true;
    }

    private void menuAuthSet(HttpSession httpSession, Integer menuSeq, List<AdminGroupAuth> adminGroupAuthList) {
        try {
            for (AdminGroupAuth adminGroupAuth : adminGroupAuthList) {
                if (adminGroupAuth.getMenuSeq().equals(menuSeq)) {
                    //httpSession.setAttribute(SessionAttrName.MENU_AUTH.toString(), adminGroupAuth.getAuth());

                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}