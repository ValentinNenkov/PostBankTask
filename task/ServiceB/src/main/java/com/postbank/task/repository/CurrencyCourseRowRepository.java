package com.postbank.task.repository;

import com.postbank.task.entity.CurrencyCourseRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyCourseRowRepository extends JpaRepository<CurrencyCourseRow, Long> {
}
