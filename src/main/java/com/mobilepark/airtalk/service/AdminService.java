package com.mobilepark.airtalk.service;

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

    public List<Admin> getAdminList(){
        logger.info("getAmdinList!");
        return adminRepository.findAll();
    }
}
