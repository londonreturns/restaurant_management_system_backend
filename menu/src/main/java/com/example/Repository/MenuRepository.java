package com.example.Repository;

import com.example.Model.MenuDB;
import com.example.Model.SizeDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuDB, Long> {
    List<MenuDB> findByCategoryId(Long categoryId);
}
