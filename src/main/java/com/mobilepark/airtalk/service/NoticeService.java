package com.mobilepark.airtalk.service;

import java.util.List;

import com.mobilepark.airtalk.data.Notice;
import com.mobilepark.airtalk.repository.NoticeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Autowired
    public NoticeRepository noticeRepository;

    public List<Notice> getAllList(){
        System.out.println(" NoticeService - getAllList()");
        return noticeRepository.findAll();
    }
}
