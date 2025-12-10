package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.CategoryDTO;
import com.empresa.biblioteca.model.Category;
import com.empresa.biblioteca.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // listar todas las categorias
    public Page<CategoryDTO> findAll(Pageable pageable) {
        var categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryDTO::new);
    }

    // agregar categoria
    public Category save(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        return categoryRepository.save(category);
    }

    // obtener categoria por id
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return new CategoryDTO(category);
    }

    // actualizar categoria
    public Category update(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalAccessError::new);
        if (categoryDTO.getName() != null) category.setName(categoryDTO.getName());
        if (categoryDTO.getDescription() != null) category.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(category);
    }

    // eliminar categoria
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    // mappera List<Category> a List<CategoryDTO>
    public List<CategoryDTO> toDTOList(List<Category> categoryList) {
        List<CategoryDTO> dtoList = new ArrayList<>();
        for (Category category : categoryList) {
            dtoList.add(new CategoryDTO(category));
        }
        return dtoList;
    }

}
