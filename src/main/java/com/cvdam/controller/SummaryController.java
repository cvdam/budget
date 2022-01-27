package com.cvdam.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvdam.model.Category;
import com.cvdam.reports.CategorySummary;
import com.cvdam.reports.Summary;
import com.cvdam.repository.CategoryRepository;
import com.cvdam.repository.ExpenseRepository;
import com.cvdam.repository.IncomeRepository;

@RestController
@RequestMapping("/summary")
public class SummaryController {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private IncomeRepository incomeRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
		
	@GetMapping("/{year}/{month}")
	public Summary generateSummary(@PathVariable Integer year, @PathVariable Integer month){
		
		LocalDate inputDate = LocalDate.of(year, month, 1);
		LocalDate startDate = inputDate.withDayOfMonth(1);
		LocalDate endDate = inputDate.withDayOfMonth(inputDate.lengthOfMonth());
		
		BigDecimal totalIncomes = incomeRepository.findTotalIncomesByDate(startDate, endDate);
		BigDecimal totalExpenses = expenseRepository.findTotalExpensesByDate(startDate, endDate);
		List<CategorySummary> expensesByCategory = expenseRepository.findTotalExpensesByCategory(startDate, endDate);

		List<Category> categories = categoryRepository.findAll();
		List<CategorySummary> summaryCategoriesResults = new ArrayList<>();
		
		for(int i =0 ; i< categories.size(); i++) {
			for(int j = 0; j <expensesByCategory.size(); j++) {
				if (categories.get(i).getName().equals(expensesByCategory.get(j).getName())) {
					summaryCategoriesResults.add(expensesByCategory.get(j));
					categories.remove(i);
				}
			}
		}
		
		BigDecimal bd = new BigDecimal(0);
		for(int c =0 ; c< categories.size(); c++) {
			CategorySummary cs = new CategorySummary(categories.get(c).getName(), bd);
			summaryCategoriesResults.add(cs);
		}
		
		Summary summary = new Summary(totalIncomes, totalExpenses, summaryCategoriesResults);
		return summary;

	}

}
