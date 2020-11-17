package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.mobilepark.airtalk.data.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer>, JpaSpecificationExecutor<Alarm> {

    int countByUserIdContaining(String keyword);
    List<Alarm> findByUserIdContaining(String keyword, PageRequest pageRequest);
}
