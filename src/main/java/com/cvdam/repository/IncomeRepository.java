package com.cvdam.repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvdam.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	Income findByDescription(@NotNull @NotEmpty String description);

}
