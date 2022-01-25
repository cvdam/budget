package com.cvdam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvdam.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	List<Income> findByDescriptionIgnoreCase(String description);
}
