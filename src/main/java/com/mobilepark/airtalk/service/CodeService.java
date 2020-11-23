package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import com.mobilepark.airtalk.data.Code;
import com.mobilepark.airtalk.repository.CodeRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

@Service
public class CodeService {
    private static final Logger logger = LoggerFactory.getLogger(CodeService.class);

    @Autowired
    public CodeRepository CodeRepository;
    
    @Autowired
    SpecificationService<Code> specificationService;
  
    // 목록 조회
    public List<Code> list() {

        List<Code> list = new ArrayList<>();
        list = CodeRepository.findAll();

        list.forEach(s -> logger.info(s.toString()));

        return list;
    }

    @Transactional
    public void create(String param) throws ParseException {
        Code Code = null;

        System.out.println("test 정보: " + param);
        JSONParser parser = new JSONParser();
        JSONObject jObject;
        jObject = (JSONObject) parser.parse(param);
        try {
            /* START */
            Code = new Code();
            Code.setCode((String) jObject.get("code"));
            Code.setCodeName((String) jObject.get("codeName"));
            Code.setRegDate(new Date());

            Code = CodeRepository.save(Code);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    public void delete(String param) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jObject;

        jObject = (JSONObject) parser.parse(param);
        String code = (String) jObject.get("code");

        System.out.println("code 정보: ----------" + code);

        try {
            /* START */
            CodeRepository.deleteById(code);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
  
  private Specification<Code> getSpecification(String type, String search) {
    
    Code code = new Code();
    Set<String> likeSet = new HashSet<>();

    if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "code")) {
      logger.info("success service");
      code.setCode(search);
      likeSet.add(type);
    }
    else{
        logger.info("success service");
        code.setCodeName(search);
        likeSet.add(type);
    }
    return specificationService.like(likeSet, code);
  }

    /**
     * 검색 조회
     * 
     * @return List<Code>
     */
    public List<Code> search(JSONObject form) {

        List<Code> list = new ArrayList<>();

        String type = form.get("type").toString();
        String keyword = form.get("keyword").toString();
        int length = Integer.parseInt(form.get("length").toString());
        int start = Integer.parseInt(form.get("start").toString());
        
        PageRequest pageRequest = PageRequest.of(start, length);

        list = CodeRepository.findAll(this.getSpecification(type, keyword), pageRequest).getContent();

        return list;
    }

    /**
     * 검색 카운트 조회
     * 
     * @return Integer
     */
  public int count(JSONObject form) {
    int count = 0;
    switch(form.get("type").toString()) {
      case "code":
        count = CodeRepository.countByCodeContaining(form.get("keyword").toString());
        break;
      case "codeName":
        count = CodeRepository.countByCodeNameContaining(form.get("keyword").toString());
        break;
    } 
    logger.info("Total Count : ["+count+"]");
    return count;
  }

}
