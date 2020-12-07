package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.repository.AdminRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminRepository adminRepository;

    @Autowired
    SpecificationService<Admin> specificationService;

    // 계정 관리 리스트
    public List<Admin> search() {
        List<Admin> adminList = new ArrayList<>();
        
        adminList = adminRepository.findAll();


        return adminList;
    }

    // 상세 페이지 정보 조회
    public Admin view(String adminId) {
        Admin adminInfo = new Admin();

        adminInfo = adminRepository.findByAdminId(adminId);

        return adminInfo;
    }

    // 계정 정보 수정
    public String update(Admin admin) {
        String result = "SUCCESS";

        try {
            Admin newAdminInfo = adminRepository.findByAdminId(admin.getAdminId());

            newAdminInfo.setAdminId(admin.getAdminId());
            newAdminInfo.setAdminName(newAdminInfo.getAdminName());
            newAdminInfo.setAdminGroupSeq(admin.getAdminGroupSeq());
            newAdminInfo.setPassword(admin.getPassword());
            newAdminInfo.setPasswordUpdateDate(new Date());
            newAdminInfo.setModDate(new Date());
            newAdminInfo.setPhone(admin.getPhone());
            newAdminInfo.setEmail(admin.getEmail());
            newAdminInfo.setRegDate(newAdminInfo.getRegDate());

            adminRepository.save(newAdminInfo);
        }
        catch (Exception e) {
            logger.info("Error : " + e);
            result = "FAIL";
            return result;
        }

        return result;
    }

    // 계정 생성
    public String create(Admin admin) {
        String result = "SUCCESS";

        try {
            admin.setRegDate(new Date());
            admin.setPasswordUpdateDate(new Date());
            adminRepository.save(admin);
        }
        catch (Exception e) {
            logger.info("Error : " + e);
            result = "FAIL";
            return result;
        }

        return result;
    }

    // 계정 삭제
    public String delete(String adminId) {
        String result = "SUCCESS";

        try {
            adminRepository.deleteById(adminId);
        }
        catch (Exception e) {
            logger.info("Error : " + e);
            result = "FAIL";
            return result;
        }
        return result;
    }

    private Specification<Admin> getSpecification(String type, String search) {
    
        Admin Admin = new Admin();
        Set<String> likeSet = new HashSet<>();
    
        if(StringUtils.isNotEmpty(search) && StringUtils.equals(type, "adminId")) {
          logger.info("success service");
          Admin.setAdminId(search);
          likeSet.add(type);
        }
        else{
            logger.info("success service");
            Admin.setAdminName(search);
            likeSet.add(type);
        }
    
        return specificationService.like(likeSet, Admin);
      }
    
        /**
         * 검색 조회
         * 
         * @return List<Code>
         */
        public List<Admin> search(JSONObject form) {
    
            List<Admin> list = new ArrayList<>();
    
            String type = form.get("type").toString();
            String keyword = form.get("keyword").toString();
            int length = Integer.parseInt(form.get("length").toString());
            int start = Integer.parseInt(form.get("start").toString());

            PageRequest pageRequest = PageRequest.of(start, length);
            if (type == "all")
                list = adminRepository.findAll(pageRequest).getContent();
            else
                list = adminRepository.findAll(this.getSpecification(type, keyword), pageRequest).getContent();
    
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
          case "all":
            count = adminRepository.countByAll();
            break;
          case "adminId":
            count = adminRepository.countByAdminIdContaining(form.get("keyword").toString());
            break;
          case "adminName":
            count = adminRepository.countByAdminNameContaining(form.get("keyword").toString());
            break;
        } 
        logger.info("Total Count : ["+count+"]");
        return count;

      }
}
