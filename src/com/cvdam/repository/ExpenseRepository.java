package com.cvdam.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cvdam.model.Expense;
import com.cvdam.reports.CategorySummary;


public interface ExpenseRepository extends JpaRepository<Expense, Long>{

	List<Expense> findByDescriptionIgnoreCase(String description);

	@Query(value = "SELECT e FROM Expense e WHERE YEAR(e.createDate) =?1 AND MONTH(e.createDate) = ?2")
    List<Expense> findByCreateDate(Integer year, Integer month);

	@Query(value = "SELECT SUM(e.value) FROM Expense e WHERE YEAR(e.createDate) =?1 AND MONTH(e.createDate) = ?2")
	BigDecimal findTotalExpensesByDate(Integer year, Integer month);

	@Query(value="SELECT new com.cvdam.reports.CategorySummary(e.category as name, SUM(e.value)) FROM Expense AS e  WHERE YEAR(e.createDate) =?1 AND MONTH(e.createDate) = ?2 GROUP BY e.category ")
	List<CategorySummary> findTotalExpensesByCategory(Integer year, Integer month);

}
