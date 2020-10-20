package com.mobilepark.airtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilepark.airtalk.data.Login;

@Repository
public class LoginRepository extends JpaRepository {
    public Boolean existsByAdminIdAndPassword(String id, String pw);
}
