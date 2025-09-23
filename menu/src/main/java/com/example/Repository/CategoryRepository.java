package com.example.Repository;

import com.example.Dto.CategoryDTO;
import com.example.Model.CategoryDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDB, Long> {

    @Query("SELECT new com.example.Dto.CategoryDTO(c.id, c.name) FROM com.example.Model.CategoryDB c")
    List<CategoryDTO> getAllCategories();
}
