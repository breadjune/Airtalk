package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.mobilepark.airtalk.data.Admin;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> ,JpaSpecificationExecutor<Admin>{

    public Admin findByAdminIdAndPassword(String email, String pw);
    public List<Admin> findByAdminName(String adminName);
    public Admin findByAdminId(String adminId);
    public Admin findByMcode(String mcode);
    public long countByPhoneAndAdminNameAndEmail(String phone, String name, String email);
    public List<Admin> findByPhoneAndAdminNameAndEmail(String phone, String name, String email);


    int countByAdminIdContaining(String keyword);
    int countByAdminNameContaining(String keyword);
    List<Admin> findByAdminIdContaining(String keyword, PageRequest pageRequest);

//    public AdminGroup findByUserId(String userId);
//    public AdminGroup findByPassword(String password);

   @Query(value = "SELECT COUNT(*) FROM TBL_ADMIN", nativeQuery = true)
   int countByAll();
}
