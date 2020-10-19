package com.mobilepark.airtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilepark.airtalk.data.MenuFunc;
import java.util.List;

@Repository
public interface MenuFuncRepository extends JpaRepository<MenuFunc,Integer>{
    public List<MenuFunc> findByMenuSeq(Integer id);
}
