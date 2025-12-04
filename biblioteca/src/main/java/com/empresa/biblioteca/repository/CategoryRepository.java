package com.empresa.biblioteca.repository;

import com.empresa.biblioteca.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
