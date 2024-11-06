package com.postbank.task.repository;

import com.postbank.task.entity.CurrencyCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyCourseRepository extends JpaRepository<CurrencyCourse, Long> {

    Optional<CurrencyCourse> findByActive(Boolean isActive);

}
