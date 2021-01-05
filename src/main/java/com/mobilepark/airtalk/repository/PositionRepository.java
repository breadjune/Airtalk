package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.Position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position,String>{
    
}
