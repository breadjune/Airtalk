package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import com.mobilepark.airtalk.data.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>, JpaSpecificationExecutor<Board> {
    int countByBcode(String bcode);
    int countByBcodeAndTitleContaining(String bcode, String keyword);
    int countByBcodeAndWriterContaining(String bcode, String keyword);
    List<Board> findByBcodeAndTitle(String bcode, String keyword);
    List<Board> findByBcodeAndWriter(String bcode, String keyword);
}
