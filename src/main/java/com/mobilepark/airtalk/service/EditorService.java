package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.mobilepark.airtalk.data.Editor;
import com.mobilepark.airtalk.repository.EditorRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorService {
    private static final Logger logger = LoggerFactory.getLogger(EditorService.class);

    @Autowired
    public EditorRepository EditorRepository;

    public List<Editor> search() {

        List<Editor> list = new ArrayList<>();
        list = EditorRepository.findAll();

        logger.info("regDate : " + list.get(0).getRegDate());

        list.forEach(s -> logger.info(s.toString()));

        return list;
    }

    @Transactional
    public void create(String test) throws ParseException {
        Editor Editor = null;

        System.out.println("test 정보: " + test);
        JSONParser parser = new JSONParser();
        JSONObject jObject;
        
            jObject = (JSONObject) parser.parse(test);
        try {
            /* START */
            Editor = new Editor();
            Editor.setAdminId((String)jObject.get("adminId"));
            Editor.setTitle((String)jObject.get("title"));
            Editor.setContents((String)jObject.get("content"));
            Editor.setRegDate(new Date());

            Editor = EditorRepository.save(Editor);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public int update(String param) throws ParseException {
        Editor Editor = null;

        System.out.println("test 정보: " + param);
        JSONParser parser = new JSONParser();
        JSONObject jObject;
        jObject = (JSONObject) parser.parse(param);

             try {
                /* START */
                // EditorRepository.findByUserId((String)jObject.get("adminId"));
                Editor = new Editor();
                Editor.setTitle((String)jObject.get("title"));
                Editor.setContents((String)jObject.get("content"));
                Editor.setAdminId((String)jObject.get("adminId"));
                Editor.setModDate(new Date());
    
                Editor = EditorRepository.save(Editor);
                return 0;
    
            } catch (Exception e) {
                logger.error(e.getMessage());
                return 1;
            }
        
    }

    @Transactional
    public void delete(String param) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jObject;

        jObject = (JSONObject) parser.parse(param);
        int editorSeq = (int)jObject.get("editorSeq");

        System.out.println("editorSeq 정보: ----------" + editorSeq);

        try {
            /* START */
            EditorRepository.deleteById(editorSeq);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}