package com.mobilepark.airtalk.repository;

import com.mobilepark.airtalk.data.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface VacationRepository extends JpaRepository<Vacation, Integer>, JpaSpecificationExecutor<Vacation> {
}
