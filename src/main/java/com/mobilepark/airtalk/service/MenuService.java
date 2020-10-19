package com.mobilepark.airtalk.service;

import java.util.List;

import com.mobilepark.airtalk.data.Menu;
import com.mobilepark.airtalk.data.MenuFunc;
import com.mobilepark.airtalk.repository.MenuFuncRepository;
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

    @Autowired
    public MenuFuncRepository menuFuncRepository;

    public List<Menu> getMenu(){
        System.out.println(" MenuService - get Menu()");
        return menuRepository.findAll();
    }

    public Menu getDetail(int seq){
        System.out.println(" MenuService - getDetail()");
        return menuRepository.findByMenuSeq(seq);
    }

    public List<MenuFunc> getMenuFunc(int seq){
        System.out.println(" MenuService - getMenuFunc()");
        return menuFuncRepository.findByMenuSeq(seq);
    }
    
}
