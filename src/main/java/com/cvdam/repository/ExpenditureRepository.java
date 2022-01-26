package com.cvdam.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cvdam.model.Expenditure;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long>{

	List<Expenditure> findByDescriptionIgnoreCase(String description);

	@Query(value = "select i from Expenditure i where i.createDate between ?1 and ?2")
    List<Expenditure> findByCreateDate(LocalDate startDate, LocalDate endDate);

}
