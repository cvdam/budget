package com.cvdam.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Expenditure {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private BigDecimal value;
	private LocalDateTime createDate = LocalDateTime.now();

	@ManyToOne
	private Category category;
	
	
	public Expenditure() {}
	
	public Expenditure(String description , BigDecimal value, Category category) {
		this.description = description;
		this.value = value;
		this.category = category;
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
		return createDate ;
	}
	public void setCreateDate(LocalDateTime createDate ) {
		this.createDate  = createDate;
	}
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
