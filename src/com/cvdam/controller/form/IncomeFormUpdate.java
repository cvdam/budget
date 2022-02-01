package com.cvdam.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cvdam.model.Income;
import com.cvdam.repository.IncomeRepository;

public class IncomeFormUpdate {
	
	@NotNull @NotEmpty
	private String description;
	
	@NotNull
	private BigDecimal value;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Income update(Long id, IncomeRepository incomeRepository) {
		
		Income income = incomeRepository.getById(id);
		income.setDescription(this.description);
		income.setValue(this.value);
		return income;
	}
}
