package com.cvdam.controller;

import java.math.BigDecimal;
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
				
		BigDecimal totalIncomes = incomeRepository.findTotalIncomesByDate(year, month);
		BigDecimal totalExpenses = expenseRepository.findTotalExpensesByDate(year, month);
		List<CategorySummary> expensesByCategory = expenseRepository.findTotalExpensesByCategory(year, month);

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
		
		for(int c =0 ; c< categories.size(); c++) {
			CategorySummary cs = new CategorySummary(categories.get(c).getName(), BigDecimal.ZERO);
			summaryCategoriesResults.add(cs);
		}
		
		Summary summary = new Summary(totalIncomes, totalExpenses, summaryCategoriesResults);
		return summary;

	}

}
