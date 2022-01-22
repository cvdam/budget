package com.cvdam.controller.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cvdam.model.Expenditure;
import com.cvdam.repository.ExpenditureRepository;

public class ExpenditureForm {

	@NotNull @NotEmpty
	private String description;
	
	@NotNull
	private BigDecimal value;
	private LocalDateTime createDate = LocalDateTime.now();


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
	
	public Expenditure convert(ExpenditureRepository expenditureRepository) {
		Expenditure expenditure = expenditureRepository.findByDescriptionIgnoreCase(description);

		if (expenditure == null) {
			return new Expenditure(description, value);
		}else {
			LocalDateTime dateNow = LocalDateTime.now();
			if (expenditure.getCreateDate().getMonth().equals(dateNow.getMonth())
					&& dateNow.getYear() == expenditure.getCreateDate().getYear()) {
				return null;	
				
			}else {
		
				return new Expenditure(description, value);
			}
		}
		
	}
}
