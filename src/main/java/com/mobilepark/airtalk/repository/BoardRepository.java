package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import com.mobilepark.airtalk.data.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>, JpaSpecificationExecutor<Board> {
    int countByBCodeAndTitleContaining(String keyword);
    int countByBCodeAndWriterContaining(String keyword);
    List<Board> findByBCodeAndTitle(String bCode, String keyword);
    List<Board> findByBCodeAndWriter(String bCode, String keyword);
}
