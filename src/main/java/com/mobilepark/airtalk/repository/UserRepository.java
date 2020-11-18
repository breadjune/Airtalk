package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.User;
import com.mobilepark.airtalk.data.UserAPI;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , String> , JpaSpecificationExecutor<User> {
   public List<UserAPI> findAllBy();
   int countByIdContaining(String keyword);
   List<User> findByIdContaining(String keyword, PageRequest pageRequest);

}