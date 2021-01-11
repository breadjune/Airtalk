package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.mobilepark.airtalk.data.MucMember;

@Repository
public interface MucMemberRepository extends JpaRepository<MucMember, Integer> {

    @Query(value = "SELECT jid FROM openfire_db.ofMucMember WHERE roomID = ?1", nativeQuery=true)
    List<String> qfindByJid(int roomID);
}
