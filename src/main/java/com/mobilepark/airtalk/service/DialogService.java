package com.mobilepark.airtalk.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilepark.airtalk.repository.UserRepository;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DialogService {
    
    private static final Logger logger = LoggerFactory.getLogger(DialogService.class);

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public FCMService fcmService;

    public Map<String, String> dialogPush(JSONObject params) {
        logger.info("dialogPush params : " + params);
        Map<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonArray = "";

        try {

            String message = params.get("message").toString();
            String title= "";
            List<Map<String, String>> paramMap = null;

            if(params.get("data").toString() != null) {
                jsonArray = mapper.writeValueAsString(params.get("data"));
                paramMap = new ObjectMapper().readValue(jsonArray, new TypeReference<List<Map<String, String>>>(){});

                if(params.get("groupYn").equals("Y")) {
                    title = params.get("roomName").toString();
                    for(int i=0; i < paramMap.size(); i++) {
                        String pushKey = userRepository.qfindByPushKey(paramMap.get(i).get("phoneNumber"));
                        fcmService.sendMessageTo(pushKey, title, message, params.get("userId").toString(), paramMap.get(i).get("name"));
                        logger.info("[0000] Group Send Message Successed");
                        map.put("err_cd", "0000");
                    }
                } else if(params.get("groupYn").equals("N")) {
                    title = params.get("userId").toString();
                    for(int i=0; i < paramMap.size(); i++) {
                        String pushKey = userRepository.qfindByPushKey(paramMap.get(i).get("phoneNumber"));
                        fcmService.sendMessageTo(pushKey, title, message, params.get("userId").toString(), paramMap.get(i).get("name"));
                        logger.info("[0000] Send Message Successed");
                        map.put("err_cd", "0000");
                    }
                } else {
                    logger.error("[-1101] groupYn value is not available");
                    map.put("err_cd", "-1101");
                }

            } else if(params.get("name").toString() != null && params.get("phoneNumber").toString() != null) {

                if(params.get("groupYn").equals("Y")) {
                    title = params.get("roomName").toString();
                        String pushKey = userRepository.qfindByPushKey(params.get("phoneNumber").toString());
                        fcmService.sendMessageTo(pushKey, title, message, params.get("userId").toString(), params.get("name").toString());
                        logger.info("[0000] Group Send Message Successed");
                        map.put("err_cd", "0000");
                    
                } else if(params.get("groupYn").equals("N")) {
                    title = params.get("userId").toString();

                    String pushKey = userRepository.qfindByPushKey(params.get("phoneNumber").toString());
                        fcmService.sendMessageTo(pushKey, title, message, params.get("userId").toString(), params.get("name").toString());
                        logger.info("[0000] Send Message Successed");
                        map.put("err_cd", "0000");
                    
                } else {
                    logger.error("[-1101] groupYn value is not available");
                    map.put("err_cd", "-1101");
                }
            }

        } catch (JsonProcessingException e){
            logger.error("[-1300] Json Processing ERROR", e);
            map.put("err_cd", "-1300");
        } catch (IOException e) {
            logger.error("[-1200] FCM Send ERROR", e);
            map.put("err_cd", "-1200");
        } catch (NullPointerException e) {
            logger.error("[-1100] parameter ERROR", e);
            map.put("err_cd", "-1100");
        }

        return map;
    }
}
