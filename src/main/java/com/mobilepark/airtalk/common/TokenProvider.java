package com.mobilepark.airtalk.common;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    // properties에서 비밀키 가지고 옴
    // @Value("${spring.jwt.token.secret-key}") String secretKey;

    // private Environment env;

    // @Autowired
    // public TokenProvider(Environment env) {
    //     this.env = env;
    // }

    private String secretKey = "AiraTalkVueBootstrapDashboardSpringbootSequrityJsonWebTokenSecretKeyProvider";

    public Key JWTAlgorithm() {
        // String secretKey = env.getProperty("spring.jwt.token.secret-key");
        // 비밀키 binary 형식으로 변환
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        // 알고리즘 지정(SHA256)
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;    
        // JWT에서 제공하는 SHA256 알고리즘으로 KEY 생성
        Key SHAkey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return SHAkey;
    }

    // 토큰 생성
    public String createToken(String name, String id) throws Exception {

        // String secretKey = env.getProperty("spring.jwt.token.secret-key");

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

    }

    //exp 생성
    public Date datePlus() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        logger.info("nowDate : " + df.format(cal.getTime()));
        cal.add(Calendar.MINUTE, 30);
        logger.info("DatePlus : " + df.format(cal.getTime()));
        return cal.getTime();

    }

    //토큰 전체 정보 확인
    public void tokenReader(String token) {

        Key SHAkey = JWTAlgorithm();

        Claims body = Jwts.parser().setSigningKey(SHAkey).parseClaimsJws(token).getBody();
        String iss = body.getIssuer();
        String sub = body.getSubject();
        Date exp = body.getExpiration();
        Date iat = body.getIssuedAt();

        logger.info("iss : " +iss+ " / sub : " +sub+ " / exp : " +exp+ " / iat : " +iat );
    }

    //토큰 만료 확인
    public Boolean validationToken(String token) {
        try {
            if (token != null) {
                Key SHAkey = JWTAlgorithm();
                Date exp = Jwts.parser().setSigningKey(SHAkey).parseClaimsJws(token).getBody().getExpiration();;
                logger.info("exp : " + exp);
                return dateCompare(exp);
            }
        } catch(Exception e) {
            // throw new UnauthorizedException();
            logger.error("Token이 만료되었습니다.", e);
        }
        return false;
    }

    //exp 날짜 비교
    public Boolean dateCompare(Date exp) {
    
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        boolean result = false;
        Date nowDate = new Date();

        logger.info("현재시간 : " + df.format(nowDate));
        logger.info("만료시간 : " + df.format(exp));

        int compare = nowDate.compareTo(exp);
        if (compare < 0) {
            result = true;
        }
        return result;
    }
}
