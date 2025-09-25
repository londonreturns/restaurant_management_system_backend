package com.example.Service.Impl;

import com.example.Dto.CategoryDTO;
import com.example.Model.CategoryDB;
import com.example.Repository.CategoryRepository;
import com.example.Service.CategoryService;
import com.example.Utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository CategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws ValidationException {
        List<CategoryDTO> categoryDTOFromDB = categoryRepository.findByName(categoryDTO.getName());
        Validator.isValidName(categoryDTO.getName(), 3, 15, categoryDTOFromDB.size());
        return convertToDto(categoryRepository.save(convertToEntity(categoryDTO)));
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {
        return convertToDto(categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Category with id " + id + " not found")));
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(
                this::convertToDto
        ).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) throws ValidationException {
        Long id = categoryDTO.getId();
        CategoryDB oldCategory = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Category with id " + id + " not found")
        );

        List<CategoryDTO> categoryDTOFromDB = categoryRepository.findByName(categoryDTO.getName());
        Validator.isValidName(categoryDTO.getName(),3, 15, categoryDTOFromDB.size());

        CategoryDB updateCategory = updateCategoryDetails(oldCategory, convertToEntity(categoryDTO));
        return convertToDto(categoryRepository.save(updateCategory));
    }

    @Override
    public String deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Category with id " + id + " not found"));

        categoryRepository.deleteById(id);
        return "Category with id " + id + " deleted";
    }

    private CategoryDB updateCategoryDetails(CategoryDB oldCategory, CategoryDB newCategory) {
        oldCategory.setName(newCategory.getName());
        return oldCategory;
    }

    private CategoryDB convertToEntity(CategoryDTO categoryDTO) {
        CategoryDB categoryDB = new CategoryDB();
        categoryDB.setId(categoryDTO.getId());
        categoryDB.setName(categoryDTO.getName());
        return categoryDB;
    }

    private CategoryDTO convertToDto(CategoryDB categoryDB) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryDB.getId());
        categoryDTO.setName(categoryDB.getName());
        return categoryDTO;
    }
}
