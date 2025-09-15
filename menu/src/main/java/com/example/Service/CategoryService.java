package com.example.Service;

import com.example.Dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO findCategoryById(Long id);

    List<CategoryDTO> findAllCategories();

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    String deleteCategory(Long id);
}
