package com.mobilepark.airtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilepark.airtalk.data.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Integer>{
    public Notice findByBoardSeq(Integer id);
}
