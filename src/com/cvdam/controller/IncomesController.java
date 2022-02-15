package com.cvdam.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@CacheEvict(value = "incomeslist", allEntries = true)
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
	public Page<IncomeDto> readIncomeByDate(@PathVariable Integer year, @PathVariable Integer month, Pageable pages){
				
		Page<Income> incomes = incomeRepository.findByCreateDate(year, month, pages);
		
		if (incomes == null | incomes.isEmpty()) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Income resources not found in the specified year and month", null);
		}
		return IncomeDto.convert(incomes);
	}
	
	@GetMapping
	@Cacheable(value = "incomesList")
	public Page<IncomeDto> readIncomes(@RequestParam(required = false) String description, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pages) {
				
		if (description == null) {
			Page<Income> incomes = incomeRepository.findAll(pages);
			return IncomeDto.convert(incomes);
		} else {
			Page<Income> incomes = incomeRepository.findByDescriptionIgnoreCase(description, pages);
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
