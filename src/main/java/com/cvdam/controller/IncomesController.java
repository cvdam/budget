package com.cvdam.controller;

import java.net.URI;
import java.time.LocalDate;
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

import com.cvdam.controller.dto.IncomeDto;
import com.cvdam.controller.form.IncomeForm;
import com.cvdam.controller.form.IncomeFormUpdate;
import com.cvdam.model.Income;
import com.cvdam.repository.IncomeRepository;


@RestController
@RequestMapping("/incomes")
public class IncomesController {
	
	@Autowired
	private IncomeRepository incomeRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<IncomeDto> createIncome(@RequestBody @Valid IncomeForm form, UriComponentsBuilder uriBuilder){
		
		Income income = form.convert(incomeRepository);
		
		if (income == null) {
			//return ResponseEntity.status(HttpStatus.CONFLICT.build();
			throw new ResponseStatusException(
			          HttpStatus.CONFLICT, "Income resource already exists for the current month and year.", null);
		}
		
		incomeRepository.save(income);
		
		URI uri = uriBuilder.path("/incomes/{id}").buildAndExpand(income.getId()).toUri();
		return ResponseEntity.created(uri).body(new IncomeDto(income));
	}
		
	
	@GetMapping("/{id}")
	public ResponseEntity<IncomeDto> readIncome(@PathVariable Long id) {
		
		Optional<Income> income = incomeRepository.findById(id);
		
		if (income.isPresent()) {
			return ResponseEntity.ok(new IncomeDto(income.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{year}/{month}")
	public List<IncomeDto> readIncomeByDate(@PathVariable Integer year, @PathVariable Integer month){
		
		LocalDate inputDate = LocalDate.of(year, month, 1);
		LocalDate startDate = inputDate.withDayOfMonth(1);
		LocalDate endDate = inputDate.withDayOfMonth(inputDate.lengthOfMonth());
		
		List<Income> incomes = incomeRepository.findByCreateDate(startDate, endDate);
		
		if (incomes == null | incomes.isEmpty()) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Income resources not found in the specified year and month", null);
		}
		return IncomeDto.convert(incomes);
	}
	
	@GetMapping
	public List<IncomeDto> readIncomes(String description) {
		if (description == null) {
			List<Income> incomes = incomeRepository.findAll();
			return IncomeDto.convert(incomes);
		} else {
			List<Income> incomes = incomeRepository.findByDescriptionIgnoreCase(description);
			return IncomeDto.convert(incomes);
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<IncomeDto> updateIncome(@PathVariable Long id, @RequestBody @Valid IncomeFormUpdate form){
		
		Optional<Income> income = incomeRepository.findById(id);
		
		if(income.isPresent()) {
			Income incomeUpdateData = form.update(id,incomeRepository);
			incomeRepository.save(incomeUpdateData);
			return ResponseEntity.ok(new IncomeDto(incomeUpdateData));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<IncomeDto> deleteIncome(@PathVariable Long id){
		
		Optional<Income> income = incomeRepository.findById(id);
		
		if(income.isPresent()) {
			incomeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}	
}
