package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mobilepark.airtalk.data.MucRoom;

@Repository
public interface MucRoomRepository extends JpaRepository<MucRoom, Integer> {
    @Query(value = "SELECT roomID FROM openfire_db.ofMucRoom WHERE name = ?1", nativeQuery=true)
    Integer qfindByRoomID(String name);
}
