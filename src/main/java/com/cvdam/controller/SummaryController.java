package com.cvdam.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvdam.reports.CategorySummary;
import com.cvdam.reports.Summary;
import com.cvdam.repository.ExpenseRepository;
import com.cvdam.repository.IncomeRepository;

@RestController
@RequestMapping("/summary")
public class SummaryController {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private IncomeRepository incomeRepository;
		
	@GetMapping("/{year}/{month}")
	public Summary generateSummary(@PathVariable Integer year, @PathVariable Integer month){
		
		LocalDate inputDate = LocalDate.of(year, month, 1);
		LocalDate startDate = inputDate.withDayOfMonth(1);
		LocalDate endDate = inputDate.withDayOfMonth(inputDate.lengthOfMonth());
		
		BigDecimal totalIncomes = incomeRepository.findTotalIncomesByDate(startDate, endDate);
		BigDecimal totalExpenses = expenseRepository.findTotalExpensesByDate(startDate, endDate);
		List<CategorySummary> expensesByCategory = expenseRepository.findTotalExpensesByCategory(startDate, endDate);

		Summary summary = new Summary(totalIncomes, totalExpenses, expensesByCategory);
		return summary;

	}

}
