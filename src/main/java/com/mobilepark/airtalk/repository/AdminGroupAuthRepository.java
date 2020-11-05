package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.AdminGroupAuth;
import com.mobilepark.airtalk.data.AdminGroupAuthPK;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminGroupAuthRepository extends JpaRepository<AdminGroupAuth, AdminGroupAuthPK> {
    public List<AdminGroupAuth> findByAdminGroupSeq(Integer adminGroupSeq, Sort sort);
    void deleteAllByAdminGroupSeqEquals(Integer adminGroupSeq);
}
