package com.cvdam.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.cvdam.model.Expenditure;

public class ExpenditureDto {
	private Long id;
	private String description;
	private BigDecimal value;
	private LocalDateTime createDate = LocalDateTime.now();
	
	public ExpenditureDto(Expenditure expenditure) {
		this.id = expenditure.getId();
		this.description = expenditure.getDescription();
		this.value = expenditure.getValue();
		this.createDate = expenditure.getCreateDate();
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
	public static List<ExpenditureDto> convert(List<Expenditure> expenditures) {
		
		return expenditures.stream().map(ExpenditureDto::new).collect(Collectors.toList());
	}	
}
