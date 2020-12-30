package com.mobilepark.airtalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.mobilepark.airtalk.data.PhoneBook;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook,Integer>{

    @Transactional
    int deleteByUserId(String UserId);
    List<PhoneBook> findByUserId(String UserId);
}
