package com.cvdam.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cvdam.model.Category;
import com.cvdam.model.Expense;
import com.cvdam.repository.CategoryRepository;
import com.cvdam.repository.ExpenseRepository;

public class ExpenseForm {
	
	
	@NotNull @NotEmpty
	private String description;
	
	@NotNull
	private BigDecimal value;
	private LocalDate createDate = LocalDate.now();
	private Category category;


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
	
	public Expense convert(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
		         	
		List<Expense> expenses = expenseRepository.findByDescriptionIgnoreCase(description);
		
	    if (category == null) {
	    	category = categoryRepository.findByName("others"); 
	    }
		
		if (expenses == null | expenses.isEmpty()  ) {
			return new Expense(description, value, category);
		}else {
			
			Collections.sort(expenses, (a,b)->a.getCreateDate().compareTo(b.getCreateDate()));
			Collections.reverse(expenses);
			
			LocalDate dateNow = LocalDate.now();
			Calendar calendarDateNow = Calendar.getInstance();
			Calendar calendarFromResource = Calendar.getInstance();

			calendarDateNow.setTime(Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			calendarFromResource.setTime(Date.from(expenses.get(0).getCreateDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			
			if(calendarDateNow.get(Calendar.YEAR) >= calendarFromResource.get(Calendar.YEAR)) {
			    if(calendarDateNow.get(Calendar.MONTH) > calendarFromResource.get(Calendar.MONTH)) {
			    	return new Expense(description, value, category);
			    }
			}
			return null;
		}
		
	}

}
