package com.mobilepark.airtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilepark.airtalk.data.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer>{
    public Menu findByMenuSeq(Integer id);
}
