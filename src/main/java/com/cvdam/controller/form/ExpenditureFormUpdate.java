package com.cvdam.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cvdam.model.Category;
import com.cvdam.model.Expenditure;
import com.cvdam.repository.CategoryRepository;
import com.cvdam.repository.ExpenditureRepository;

public class ExpenditureFormUpdate {
	
	@NotNull @NotEmpty
	private String description;
	
	@NotNull
	private BigDecimal value;
	
	private Category category;
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public Expenditure update(Long id, ExpenditureRepository expenditureRepository, CategoryRepository categoryRepository) {
		
	    if (category == null) {
	    	category = categoryRepository.findByName("others"); 
	    }
	    
		Expenditure expenditure = expenditureRepository.getById(id);
		expenditure.setDescription(this.description);
		expenditure.setValue(this.value);
		expenditure.setCategory(this.category);
		return expenditure;
	}

}
