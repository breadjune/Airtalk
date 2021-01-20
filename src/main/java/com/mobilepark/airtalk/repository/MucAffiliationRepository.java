package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.mobilepark.airtalk.data.MucAffiliation;

@Repository
public interface MucAffiliationRepository extends JpaRepository<MucAffiliation, Integer> {

    @Query(value = "SELECT jid FROM openfire_db.ofMucAffiliation WHERE roomID = ?1", nativeQuery=true)
    List<String> qfindByJid(int roomID);
}
