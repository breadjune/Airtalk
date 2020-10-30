package com.mobilepark.airtalk.service;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.repository.AdminRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public AdminRepository adminRepository;

    // properties에서 비밀키 가지고 옴
    @Value("${spring.jwt.token.secret-key}") String secretKey;
    
    // Login check
    public Admin isLogin(String email, String pw) {
        logger.info("isLogin() invoked");
        return adminRepository.findByAdminIdAndPassword(email, pw);
    }

    public Key JWTAlgorithm() {

        // 비밀키 binary 형식으로 변환
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        // 알고리즘 지정(SHA256)
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;    
        // JWT에서 제공하는 SHA256 알고리즘으로 KEY 생성
        Key SHAkey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return SHAkey;
    }

    // Token Create
    public String create(String name, String id) throws Exception {

        logger.info("secretKey : " + secretKey);
        logger.info("validation : " + System.currentTimeMillis());

        Key SHAkey = JWTAlgorithm();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        String jwt = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setIssuer(name)
                    .setSubject(id)
                    .setExpiration(datePlus())
                    .setIssuedAt(new Date())
                    .signWith(SHAkey, signatureAlgorithm)
                    .compact();

        return jwt;

    } // create end

    public void tokenReader(String jwt) {

        Key SHAkey = JWTAlgorithm();

        Claims body = Jwts.parser().setSigningKey(SHAkey).parseClaimsJws(jwt).getBody();
        String iss = body.getIssuer();
        String sub = body.getSubject();
        Date exp = body.getExpiration();
        Date iat = body.getIssuedAt();

        logger.info("iss : " +iss+ " / sub : " +sub+ " / exp : " +exp+ " / iat : " +iat );
    }

    public Boolean validationToken(String token) {
        try {
            if (token != null) {

                Key SHAkey = JWTAlgorithm();

                Jwts.parser().setSigningKey(SHAkey).parseClaimsJws(token);
                return true;
                
            }
        } catch(Exception e) {
            // throw new UnauthorizedException();
            logger.error("Token이 만료되었습니다.", e);
        }
        

        return false;
    } // validationToken end

    public Date datePlus() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        logger.info("nowDate : " + df.format(cal.getTime()));

        cal.add(Calendar.MINUTE, 30);

        logger.info("DatePlus : " + df.format(cal.getTime()));

        return cal.getTime();
    }

} //loginService end 
