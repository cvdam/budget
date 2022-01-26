package com.cvdam.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.cvdam.model.Category;
import com.cvdam.model.Expense;

public class ExpenseDto {
	private Long id;
	private String description;
	private BigDecimal value;
	private LocalDate createDate = LocalDate.now();
	private Category category;
	
	public ExpenseDto(Expense expense) {
		this.id = expense.getId();
		this.description = expense.getDescription();
		this.value = expense.getValue();
		this.createDate = expense.getCreateDate();
		this.category = expense.getCategory();
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
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public static List<ExpenseDto> convert(List<Expense> expenses) {
		return expenses.stream().map(ExpenseDto::new).collect(Collectors.toList());
	}	
}
