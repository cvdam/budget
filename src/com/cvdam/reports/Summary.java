package com.cvdam.reports;

import java.math.BigDecimal;
import java.util.List;


public class Summary {

	private BigDecimal totalIncomes;
	private BigDecimal totalExpenses;
	private BigDecimal total;
	private List<CategorySummary> categories;
		

	public Summary() {}
		
	public Summary(BigDecimal totalIncomes, BigDecimal totalExpenses, List<CategorySummary> categories) {
		this.totalIncomes = totalIncomes;
		this.totalExpenses = totalExpenses;
		this.total = totalIncomes.subtract(totalExpenses);
		this.categories = categories;
	}
	
	public BigDecimal getTotalIncomes() {
		return totalIncomes;
	}
	public void setTotalIncomes(BigDecimal totalIncomes) {
		this.totalIncomes = totalIncomes;
	}
	public BigDecimal getTotalExpenses() {
		return totalExpenses;
	}
	public void setTotalExpenses(BigDecimal totalExpenses) {
		this.totalExpenses = totalExpenses;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public List<CategorySummary> getCategories() {
		return categories;
	}

	public void setCategories(List<CategorySummary> categories) {
		this.categories = categories;
	}
	
}
