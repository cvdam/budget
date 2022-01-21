package com.cvdam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvdam.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	Income findByDescription(String description);

}
