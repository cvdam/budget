package com.cvdam.reports;

import java.math.BigDecimal;

import javax.persistence.Column;

public class CategorySummary {
	@Column(name="name", nullable = true) 
	private String name;
	@Column(name="value", nullable = true) 
	private BigDecimal value;
	
	public CategorySummary(String name, BigDecimal value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
}
