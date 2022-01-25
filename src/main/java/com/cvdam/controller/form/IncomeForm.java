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

import com.cvdam.model.Income;
import com.cvdam.repository.IncomeRepository;


public class IncomeForm {
	
	@NotNull @NotEmpty
	private String description;
	
	@NotNull
	private BigDecimal value;
	private LocalDate createDate = LocalDate.now();


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
	
	public Income convert(IncomeRepository incomeRepository) {
		
		List<Income> incomes = incomeRepository.findByDescriptionIgnoreCase(description);

		if (incomes == null | incomes.isEmpty()) {
			return new Income(description, value);
		}else {			
			
			Collections.sort(incomes, (a,b)->a.getCreateDate().compareTo(b.getCreateDate()));
			Collections.reverse(incomes);
			
			LocalDate dateNow = LocalDate.now();
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			cal1.setTime(Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			cal2.setTime(Date.from(incomes.get(0).getCreateDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			
			if(cal1.get(Calendar.YEAR) >= cal2.get(Calendar.YEAR)) {
			    if(cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
			    	return new Income(description, value);
			    }
			}
			return null;
		}		
		
	}
}
