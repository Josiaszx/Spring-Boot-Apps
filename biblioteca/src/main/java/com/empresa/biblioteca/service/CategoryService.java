package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.CategoryDTO;
import com.empresa.biblioteca.model.Category;
import com.empresa.biblioteca.repository.CategoryRepository;
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
    public List<CategoryDTO> findAll() {
        var categories = categoryRepository.findAll();
        return toDTOList(categories);
    }

    // agregar categoria
    public Category save(CategoryDTO categoryDTO) {
        Category category = toEntity(categoryDTO);
        return categoryRepository.save(category);
    }

    // obtener categoria por id
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return toDTO(category);
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

    // metodos para mappear clases
    // mappear CategoryDTO a Category
    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    // mappear Category a CategoryDTO
    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    // mappera List<Category> a List<CategoryDTO>
    public List<CategoryDTO> toDTOList(List<Category> categoryList) {
        List<CategoryDTO> dtoList = new ArrayList<>();
        for (Category category : categoryList) {
            dtoList.add(toDTO(category));
        }
        return dtoList;
    }

}
