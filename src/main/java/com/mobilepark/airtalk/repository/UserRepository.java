package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.User;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , String> , JpaSpecificationExecutor<User> {
   // public List<UserAPI> findAllBy();
   int countByUserIdContaining(String keyword);
   int countByNameContaining(String keyword);
   List<User> findByUserIdContaining(String keyword, PageRequest pageRequest);

   public User findByUserId(String userId);
   public User findByPasswordAndUserId(String password , String userId);

   @Query(value = "SELECT COUNT(*) FROM TBL_USER", nativeQuery = true)
   int countByAll();
}