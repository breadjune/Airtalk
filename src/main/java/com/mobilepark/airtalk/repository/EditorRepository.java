package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Integer> {

}
