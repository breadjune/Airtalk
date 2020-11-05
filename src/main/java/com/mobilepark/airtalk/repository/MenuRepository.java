package com.mobilepark.airtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import com.mobilepark.airtalk.data.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer>{
    public Menu findByMenuSeq(Integer id);

    public List<Menu> findByParentSeqNot(Integer parentSeq, Sort sort);
}
