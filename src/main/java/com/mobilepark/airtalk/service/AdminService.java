package com.mobilepark.airtalk.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public List<Admin> getAdminList() {
        List<Admin> adminList = new ArrayList<>();
        
        adminList = adminRepository.findAll();

        int i = 0;
        // regDate format 변경
        for (Admin task : adminList) {
            String getReqDate = task.getRegDate();
            task.setRegDate(getReqDate.substring(0, 10));
            adminList.set(i, task);

            if(task.getModDate() != null) {
                String getModDate = task.getModDate();
                task.setModDate(getModDate.substring(0, 10));
                adminList.set(i, task);
            }

            i++;
        }

        return adminList;
    }

    public List<Admin> getAdminInfo() {
        List<Admin> adminInfo = new ArrayList<>();

        int i = 0;
        for (Admin task : adminInfo) {
            String getReqDate = task.getRegDate();
            task.setRegDate(getReqDate.substring(0, 10));
            adminInfo.set(i, task);
            
            if(task.getModDate() != null) {
                String getModDate = task.getModDate();
                task.setModDate(getModDate.substring(0, 10));
                adminInfo.set(i, task);
            }

            i++;
        }

        return adminInfo;
    }
}
