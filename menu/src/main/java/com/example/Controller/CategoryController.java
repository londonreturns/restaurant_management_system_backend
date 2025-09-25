package com.example.Controller;

import com.example.Dto.CategoryDTO;
import com.example.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
@Slf4j
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        try {
            return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
        }catch (ValidationException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<>(categoryService.findAllCategories(), HttpStatus.OK);
    }

    @PutMapping("/updateCategories")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO category) {
        try {
            return new ResponseEntity<>(categoryService.updateCategory(category), HttpStatus.OK);
        } catch (ValidationException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }

    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.OK);
    }
}
