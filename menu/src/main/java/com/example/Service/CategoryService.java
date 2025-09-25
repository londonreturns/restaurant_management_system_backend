package com.example.Service;

import com.example.Dto.CategoryDTO;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO) throws ValidationException;

    CategoryDTO findCategoryById(Long id);

    List<CategoryDTO> findAllCategories();

    CategoryDTO updateCategory(CategoryDTO categoryDTO) throws ValidationException;

    String deleteCategory(Long id);
}
