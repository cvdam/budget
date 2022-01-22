package com.cvdam.repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvdam.model.Expenditure;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long>{

	Expenditure findByDescriptionIgnoreCase(@NotNull @NotEmpty String description);

}
