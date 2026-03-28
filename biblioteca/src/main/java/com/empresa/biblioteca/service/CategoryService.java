package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.CategoryDTO;
import com.empresa.biblioteca.model.Category;
import com.empresa.biblioteca.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category save(CategoryDTO categoryDTO) {
        var category = new Category(categoryDTO);
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
    }

    public Category update(CategoryDTO categoryDTO, Long id) {
        var category = findById(id);
        if (categoryDTO.getName() != null) category.setName(categoryDTO.getName());
        if (categoryDTO.getDescription() != null) category.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        if (categoryRepository.findById(id).isEmpty()) throw new NoSuchElementException("Category not found");
        categoryRepository.deleteById(id);
    }

    // ---- Metodos de mappeo ----
    // mappera List<Category> a List<CategoryDTO>
    public List<CategoryDTO> toDTOList(List<Category> categoryList) {
        List<CategoryDTO> dtoList = new ArrayList<>();
        for (Category category : categoryList) {
            dtoList.add(new CategoryDTO(category));
        }
        return dtoList;
    }
}
