package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobilepark.airtalk.data.Admin;
import com.mobilepark.airtalk.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminRepository adminRepository;

    // 계정 관리 리스트
    public List<Admin> getAdminList() {
        List<Admin> adminList = new ArrayList<>();
        
        adminList = adminRepository.findAll();


        return adminList;
    }

    // 상세 페이지 정보 조회
    public Admin getAdminInfo(String adminId) {
        Admin adminInfo = new Admin();

        adminInfo = adminRepository.findByAdminId(adminId);

        return adminInfo;
    }

    // 계정 정보 수정
    public String updateAdminInfo(Admin admin) {
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
    public String createAdmin(Admin admin) {
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
    public String deleteAdmin(String adminId) {
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
}
