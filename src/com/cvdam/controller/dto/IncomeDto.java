package com.cvdam.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.cvdam.model.Income;


public class IncomeDto {
	
	private Long id;
	private String description;
	private BigDecimal value;
	private LocalDate createDate = LocalDate.now();
	
	public IncomeDto(Income income) {
		this.id = income.getId();
		this.description = income.getDescription();
		this.value = income.getValue();
		this.createDate = income.getCreateDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
	public static List<IncomeDto> convert(List<Income> incomes) {
		return incomes.stream().map(IncomeDto::new).collect(Collectors.toList());
	}	

}
