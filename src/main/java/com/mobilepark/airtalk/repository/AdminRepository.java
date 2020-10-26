package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilepark.airtalk.data.Admin;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    public Admin findByAdminIdAndPassword(String email, String pw);

    public List<Admin> findByAdminName(String adminName);

    public Admin findByAdminId(String adminId);

    public Admin findByMcode(String mcode);

    public long countByPhoneAndAdminNameAndEmail(String phone, String name, String email);

    public List<Admin> findByPhoneAndAdminNameAndEmail(String phone, String name, String email);

}
