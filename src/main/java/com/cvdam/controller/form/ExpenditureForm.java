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
import com.cvdam.model.Expenditure;
import com.cvdam.repository.CategoryRepository;
import com.cvdam.repository.ExpenditureRepository;

public class ExpenditureForm {
	
	
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
	
	public Expenditure convert(ExpenditureRepository expenditureRepository, CategoryRepository categoryRepository) {
		         	
		List<Expenditure> expenditures = expenditureRepository.findByDescriptionIgnoreCase(description);
		
	    if (category == null) {
	    	category = categoryRepository.findByName("others"); 
	    }
		
		if (expenditures == null | expenditures.isEmpty()  ) {
			return new Expenditure(description, value, category);
		}else {
			
			Collections.sort(expenditures, (a,b)->a.getCreateDate().compareTo(b.getCreateDate()));
			Collections.reverse(expenditures);
			
			LocalDate dateNow = LocalDate.now();
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			cal1.setTime(Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			cal2.setTime(Date.from(expenditures.get(0).getCreateDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			
			if(cal1.get(Calendar.YEAR) >= cal2.get(Calendar.YEAR)) {
			    if(cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
			    	return new Expenditure(description, value, category);
			    }
			}
			return null;
		}
		
	}

}
