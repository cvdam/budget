package com.cvdam.repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cvdam.model.Expense;
import com.cvdam.reports.CategorySummary;


public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	List<Expense> findByDescriptionIgnoreCase(String description);

	@Query(value = "SELECT e FROM Expense e WHERE e.createDate BETWEEN ?1 and ?2")
    List<Expense> findByCreateDate(LocalDate startDate, LocalDate endDate);

	@Query(value = "SELECT SUM(e.value) FROM Expense e WHERE e.createDate BETWEEN ?1 and ?2")
	BigDecimal findTotalExpensesByDate(LocalDate startDate, LocalDate endDate);

	@Query(value="SELECT new com.cvdam.reports.CategorySummary(c.name as name, SUM(e.value) AS total) FROM Expense AS e RIGHT JOIN e.category AS c WHERE create_date BETWEEN ?1 and ?2  GROUP BY c.name ")
	List<CategorySummary> findTotalExpensesByCategory(LocalDate startDate, LocalDate endDate);

}
