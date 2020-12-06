package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilepark.airtalk.data.FileData;

@Repository
public interface FileRepository extends JpaRepository<FileData, Integer>{
    FileData findBySeq(int seq);
}
