package com.mobilepark.airtalk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import com.mobilepark.airtalk.data.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
    int countBySeq(int seq);
    List<Comment> findBySeqOrderByRegDateDesc(int seq);
}

