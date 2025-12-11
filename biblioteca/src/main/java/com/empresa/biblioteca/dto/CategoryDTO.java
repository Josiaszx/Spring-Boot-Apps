package com.empresa.biblioteca.dto;

import com.empresa.biblioteca.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryDTO {

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @Size(max = 50, message = "name cannot be larger than 50")
    private String name;

    @NotNull(message = "description cannot be null")
    @NotEmpty(message = "description cannot be empty")
    @Size(max = 255, message = "description cannot be larger than 255")
    private String description;

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryDTO(Category category) {
        this.name = category.getName();
        this.description = category.getDescription();
    }

    public CategoryDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
