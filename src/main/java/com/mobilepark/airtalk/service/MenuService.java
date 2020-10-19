package com.mobilepark.airtalk.service;

import java.util.List;

import com.mobilepark.airtalk.data.Menu;
import com.mobilepark.airtalk.repository.MenuRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    public MenuRepository menuRepository;

    public List<Menu> getMenu(){
        System.out.println(" MenuService - get Menu()");
        return menuRepository.findAll();
    }
    
}
