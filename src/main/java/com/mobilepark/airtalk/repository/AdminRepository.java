package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.mobilepark.airtalk.data.Admin;

@Repository
public class AdminRepository extends JpaRepository<Admin, String>{

	public List<Admin> findAll() {
		return null;
	}
    
}
