package com.mobilepark.airtalk.repository;

import java.util.List;

import com.mobilepark.airtalk.data.AdminGroup;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminGroupRepository extends JpaRepository<AdminGroup, Integer> ,JpaSpecificationExecutor<AdminGroup>{

    int countByNameContaining(String keyword);
    int countByDescriptionContaining(String keyword);
   List<AdminGroup> findByNameContaining(String keyword, PageRequest pageRequest);

//    public AdminGroup findByUserId(String userId);
//    public AdminGroup findByPassword(String password);

   @Query(value = "SELECT COUNT(*) FROM TBL_ADMIN_GROUP", nativeQuery = true)
   int countByAll();

}
