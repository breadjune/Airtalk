package com.mobilepark.airtalk.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public List<Admin> getAdminList() {
        List<Admin> adminList = new ArrayList<>();
        
        adminList = adminRepository.findAll();


        return adminList;
    }

    public Admin getAdminInfo(String adminId) {
        Admin adminInfo = new Admin();

        adminInfo = adminRepository.findByAdminId(adminId);

        return adminInfo;
    }

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
}
