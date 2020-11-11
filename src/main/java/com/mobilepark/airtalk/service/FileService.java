package com.mobilepark.airtalk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import com.mobilepark.airtalk.data.BoardFile;
import com.mobilepark.airtalk.repository.FileRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    public FileRepository FileRepository;
    
    public List<BoardFile> search() {
        
        List<BoardFile> list = new ArrayList<>();
        list = FileRepository.findAll();

        logger.info("regDate : " + list.get(0).getRegDate());

        list.forEach(s -> logger.info(s.toString()));

        return list;
    }

} //loginService end 
