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
		
		Expense expense = form.convert(expenseRepository, categoryRepository);
		
		if (expense == null) {
			throw new ResponseStatusException(
			          HttpStatus.CONFLICT, "Expense resource already exists for the current month and year.", null);
		}
		
		expenseRepository.save(expense);
		
		URI uri = uriBuilder.path("/incomes/{id}").buildAndExpand(expense.getId()).toUri();
		return ResponseEntity.created(uri).body(new ExpenseDto(expense));
	}
	
	@GetMapping
	public List<ExpenseDto> readExpenses(String description) {
		if (description == null) {
			List<Expense> expenses = expenseRepository.findAll();
			return ExpenseDto.convert(expenses);
		} else {
			List<Expense> expenses = expenseRepository.findByDescriptionIgnoreCase(description);
			return ExpenseDto.convert(expenses);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ExpenseDto> readExpense(@PathVariable Long id) {
		
		Optional<Expense> expense = expenseRepository.findById(id);
		
		if (expense.isPresent()) {
			return ResponseEntity.ok(new ExpenseDto(expense.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{year}/{month}")
	public List<ExpenseDto> readExpenseByDate(@PathVariable Integer year, @PathVariable Integer month){
				
		List<Expense> expenses = expenseRepository.findByCreateDate(year, month);
		
		if (expenses == null | expenses.isEmpty()) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Expense resources not found in the specified year and month", null);
		}
		return ExpenseDto.convert(expenses);
	}
		
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @RequestBody @Valid ExpenseFormUpdate form){
		
		Optional<Expense> expense = expenseRepository.findById(id);
		
		if(expense.isPresent()) {
			Expense expenseUpdateData = form.update(id,expenseRepository, categoryRepository);
			expenseRepository.save(expenseUpdateData);
			return ResponseEntity.ok(new ExpenseDto(expenseUpdateData));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<ExpenseDto> deleteExpense(@PathVariable Long id){
		
		Optional<Expense> expense = expenseRepository.findById(id);
		
		if(expense.isPresent()) {
			expenseRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
