package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilepark.airtalk.data.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}
