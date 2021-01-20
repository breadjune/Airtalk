package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.User;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , String> , JpaSpecificationExecutor<User> {
   // public List<UserAPI> findAllBy();
   int countByUserIdContaining(String keyword);
   int countByNameContaining(String keyword);
   List<User> findByUserIdContaining(String keyword, PageRequest pageRequest);

   public User findByUserId(String userId);
   public User findByPasswordAndUserId(String password , String userId);

   // boolean existsById(String id);

   @Query(value = "SELECT PUSH_KEY FROM TBL_USER WHERE ID = ?1", nativeQuery = true)
   String qfindByPushKey(String userId);

   @Modifying
   @Transactional
   @Query(value = "UPDATE TBL_USER SET PUSH_KEY = ?1 WHERE ID = ?2", nativeQuery = true)
   int qUpdatePushKey(String pushKey, String userId);

   @Query(value = "SELECT COUNT(*) FROM TBL_USER", nativeQuery = true)
   int countByAll();
}