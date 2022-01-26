package com.cvdam.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cvdam.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	List<Expense> findByDescriptionIgnoreCase(String description);

	@Query(value = "select e from Expense e where e.createDate between ?1 and ?2")
    List<Expense> findByCreateDate(LocalDate startDate, LocalDate endDate);

}
