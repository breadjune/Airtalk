package com.mobilepark.airtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mobilepark.airtalk.data.Position;
import com.mobilepark.airtalk.repository.PositionRepository;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PositionService {
    
    private static final Logger logger = LoggerFactory.getLogger(PositionService.class);

    @Autowired
    public PositionRepository positionRepository;

    public Map<String, String> create(JSONObject params) {

        Map<String, String> map = new HashMap<>();
        String result = "0000";

        try {
            logger.info(" Position Create Params : " + params);
            result = getParameter(params, "create");
            map.put("err_cd", "0000");
        } catch(Exception e) {
            e.printStackTrace();
            map.put("err_cd", "-1000");
        }

        return map;
    }

    public Map<String, String> update(JSONObject params) {

        Map<String, String> map = new HashMap<>();
        String result = "0000";

        try {
            logger.info(" Position Update Params : " + params);
            result = getParameter(params, "update");
            map.put("err_cd", result);
        } catch(Exception e) {
            e.printStackTrace();
            map.put("err_cd", result);
        }

        return map;
    }

    public String getParameter(JSONObject params, String type) {

        Position position = new Position();
        String result = "0000";

        position.setUserId(params.get("userId").toString());
        position.setLatitude(Double.parseDouble(params.get("userId").toString()));
        position.setLongitude(Double.parseDouble(params.get("userId").toString()));
        position.setAddress(params.get("userId").toString());
        position.setDescription(params.get("userId").toString());
        if(type.equals("create")) position.setRegDate(new Date());
        position.setModDate(new Date());

        try {
        positionRepository.save(position);
        result = "0000";
        } catch (Exception e) {
            logger.info("update error");
            result = "-1001";
            e.printStackTrace();
        }
        return result;
    }
}
