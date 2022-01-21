package com.cvdam.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.cvdam.controller.dto.IncomeDto;
import com.cvdam.controller.form.IncomeForm;
import com.cvdam.model.Income;
import com.cvdam.repository.IncomeRepository;

@RestController
@RequestMapping("/incomes")
public class IncomesController {
	
	@Autowired
	private IncomeRepository incomeRepository;
	
	@PostMapping
	public ResponseEntity<IncomeDto> createIncome(@RequestBody @Valid IncomeForm form, UriComponentsBuilder uriBuilder){
		
		Income income = form.convert(incomeRepository);
		
		if (income == null) {
			//return ResponseEntity.status(HttpStatus.CONFLICT.build();
			throw new ResponseStatusException(
			          HttpStatus.CONFLICT, "Resource already exixts for the current month and year.", null);
		}
		
		incomeRepository.save(income);
		
		URI uri = uriBuilder.path("/incomes/{id}").buildAndExpand(income.getId()).toUri();
		return ResponseEntity.created(uri).body(new IncomeDto(income));
	}
	
	@GetMapping
	public List<IncomeDto> readAllIncomes(){
		List<Income> incomes = incomeRepository.findAll();
		return IncomeDto.convert(incomes);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<IncomeDto> readIncome(@PathVariable Long id) {
		
		Optional<Income> income = incomeRepository.findById(id);
		
		if (income.isPresent()) {
			return ResponseEntity.ok(new IncomeDto(income.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
