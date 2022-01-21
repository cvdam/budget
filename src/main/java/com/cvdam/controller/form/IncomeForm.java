package com.cvdam.controller.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cvdam.model.Income;
import com.cvdam.repository.IncomeRepository;


public class IncomeForm {
	
	@NotNull @NotEmpty
	private String description;
	
	@NotNull
	private BigDecimal value;
	private LocalDateTime createDate = LocalDateTime.now();


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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
	public Income convert(IncomeRepository incomeRepository) {
		Income income = incomeRepository.findByDescription(description);

		if (income == null) {
			return new Income(description, value);
		}else {
			LocalDateTime dateNow = LocalDateTime.now();
			if (income.getCreateDate().getMonth().equals(dateNow.getMonth())
					&& dateNow.getYear() == income.getCreateDate().getYear()) {
				return null;	
				
			}else {
		
				return new Income(description, value);
			}
		}
		
	}

}
