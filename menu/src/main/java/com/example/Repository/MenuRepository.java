package com.example.Repository;

import com.example.Model.MenuDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuDB, Long> {
    List<MenuDB> findByCategoryId(Long categoryId);

    @Query("SELECT m FROM menus m " +
            "LEFT JOIN FETCH m.category " +
            "LEFT JOIN FETCH m.menuSizes ms " +
            "LEFT JOIN FETCH ms.size s")
    List<MenuDB> findAllWithSizesAndCategories();
}
