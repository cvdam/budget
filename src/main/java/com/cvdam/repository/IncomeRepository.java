package com.cvdam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cvdam.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	List<Income> findByDescriptionIgnoreCase(String description);

    @Query(value = "select i from Income i where i.createDate between ?1 and ?2")
    List<Income> findByCreateDate(LocalDate startDate, LocalDate endDate);
}
