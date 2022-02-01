package com.cvdam.reports;

import java.math.BigDecimal;

import com.cvdam.model.Category;

public class CategorySummary {
	private Category category;
	private BigDecimal value;
	
	public CategorySummary(Category category, BigDecimal value) {
		this.category = category;
		this.value = value;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
