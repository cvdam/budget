package com.cvdam.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvdam.model.Expenditure;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long>{

	List<Expenditure> findByDescriptionIgnoreCase(String description);

}
