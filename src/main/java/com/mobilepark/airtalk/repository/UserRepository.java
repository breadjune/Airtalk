package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.User;
import com.mobilepark.airtalk.data.UserAPI;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , String> {
   public List<UserAPI> findAllBy();

}