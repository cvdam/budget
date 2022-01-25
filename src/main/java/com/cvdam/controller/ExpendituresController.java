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

import com.cvdam.controller.dto.ExpenditureDto;
import com.cvdam.controller.form.ExpenditureForm;
import com.cvdam.controller.form.ExpenditureFormUpdate;
import com.cvdam.model.Expenditure;
import com.cvdam.repository.CategoryRepository;
import com.cvdam.repository.ExpenditureRepository;

@RestController
@RequestMapping("/expenditures")
public class ExpendituresController {
	
	@Autowired
	private ExpenditureRepository expenditureRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<ExpenditureDto> createExpenditure(@RequestBody @Valid ExpenditureForm form, UriComponentsBuilder uriBuilder){
		
		Expenditure expenditure = form.convert(expenditureRepository, categoryRepository);
		
		if (expenditure == null) {
			throw new ResponseStatusException(
			          HttpStatus.CONFLICT, "Expenditure resource already exixts for the current month and year.", null);
		}
		
		expenditureRepository.save(expenditure);
		
		URI uri = uriBuilder.path("/incomes/{id}").buildAndExpand(expenditure.getId()).toUri();
		return ResponseEntity.created(uri).body(new ExpenditureDto(expenditure));
	}
	
	@GetMapping
	public List<ExpenditureDto> readExpenditures(String description) {
		if (description == null) {
			List<Expenditure> expenditures = expenditureRepository.findAll();
			return ExpenditureDto.convert(expenditures);
		} else {
			List<Expenditure> expenditures = expenditureRepository.findByDescriptionIgnoreCase(description);
			return ExpenditureDto.convert(expenditures);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ExpenditureDto> readExpenditure(@PathVariable Long id) {
		
		Optional<Expenditure> expenditure = expenditureRepository.findById(id);
		
		if (expenditure.isPresent()) {
			return ResponseEntity.ok(new ExpenditureDto(expenditure.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
		
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ExpenditureDto> updateExpenditure(@PathVariable Long id, @RequestBody @Valid ExpenditureFormUpdate form){
		
		Optional<Expenditure> expenditure = expenditureRepository.findById(id);
		
		if(expenditure.isPresent()) {
			Expenditure expenditureUpdateData = form.update(id,expenditureRepository, categoryRepository);
			expenditureRepository.save(expenditureUpdateData);
			return ResponseEntity.ok(new ExpenditureDto(expenditureUpdateData));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<ExpenditureDto> deleteExpenditure(@PathVariable Long id){
		
		Optional<Expenditure> expenditure = expenditureRepository.findById(id);
		
		if(expenditure.isPresent()) {
			expenditureRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
