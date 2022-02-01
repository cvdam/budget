package com.cvdam.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cvdam.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	List<Income> findByDescriptionIgnoreCase(String description);

    @Query(value = "select i from Income i where YEAR(i.createDate) = ?1 AND MONTH(i.createDate) = ?2")
    List<Income> findByCreateDate(Integer year, Integer month);

	@Query(value = "select SUM(i.value) from Income i where YEAR(i.createDate) = ?1 AND MONTH(i.createDate) = ?2")
	BigDecimal findTotalIncomesByDate(Integer year, Integer month);
}
