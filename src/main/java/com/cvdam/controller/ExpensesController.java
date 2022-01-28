package com.cvdam.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.cvdam.controller.dto.ExpenseDto;
import com.cvdam.controller.form.ExpenseForm;
import com.cvdam.controller.form.ExpenseFormUpdate;
import com.cvdam.model.Expense;
import com.cvdam.repository.CategoryRepository;
import com.cvdam.repository.ExpenseRepository;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<ExpenseDto> createExpense(@RequestBody @Valid ExpenseForm form, UriComponentsBuilder uriBuilder){
		
		Expense expenditure = form.convert(expenseRepository, categoryRepository);
		
		if (expenditure == null) {
			throw new ResponseStatusException(
			          HttpStatus.CONFLICT, "Expense resource already exists for the current month and year.", null);
		}
		
		expenseRepository.save(expenditure);
		
		URI uri = uriBuilder.path("/incomes/{id}").buildAndExpand(expenditure.getId()).toUri();
		return ResponseEntity.created(uri).body(new ExpenseDto(expenditure));
	}
	
	@GetMapping
	public List<ExpenseDto> readExpenditures(String description) {
		if (description == null) {
			List<Expense> expenditures = expenseRepository.findAll();
			return ExpenseDto.convert(expenditures);
		} else {
			List<Expense> expenditures = expenseRepository.findByDescriptionIgnoreCase(description);
			return ExpenseDto.convert(expenditures);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ExpenseDto> readExpenditure(@PathVariable Long id) {
		
		Optional<Expense> expenditure = expenseRepository.findById(id);
		
		if (expenditure.isPresent()) {
			return ResponseEntity.ok(new ExpenseDto(expenditure.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{year}/{month}")
	public List<ExpenseDto> readExpenditureByDate(@PathVariable Integer year, @PathVariable Integer month){
				
		List<Expense> expenditures = expenseRepository.findByCreateDate(year, month);
		
		if (expenditures == null | expenditures.isEmpty()) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Expense resources not found in the specified year and month", null);
		}
		return ExpenseDto.convert(expenditures);
	}
		
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ExpenseDto> updateExpenditure(@PathVariable Long id, @RequestBody @Valid ExpenseFormUpdate form){
		
		Optional<Expense> expenditure = expenseRepository.findById(id);
		
		if(expenditure.isPresent()) {
			Expense expenditureUpdateData = form.update(id,expenseRepository, categoryRepository);
			expenseRepository.save(expenditureUpdateData);
			return ResponseEntity.ok(new ExpenseDto(expenditureUpdateData));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<ExpenseDto> deleteExpenditure(@PathVariable Long id){
		
		Optional<Expense> expenditure = expenseRepository.findById(id);
		
		if(expenditure.isPresent()) {
			expenseRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
