package com.cvdam.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cvdam.model.Expenditure;
import com.cvdam.repository.ExpenditureRepository;

public class ExpenditureFormUpdate {
	
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

	public Expenditure update(Long id, ExpenditureRepository expenditureRepository) {
		
		Expenditure expenditure = expenditureRepository.getById(id);
		expenditure.setDescription(this.description);
		expenditure.setValue(this.value);
		return expenditure;
	}

}
