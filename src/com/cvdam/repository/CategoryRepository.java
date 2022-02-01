package com.cvdam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvdam.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String name);

}
