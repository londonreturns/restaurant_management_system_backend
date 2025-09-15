package com.example.Repository;

import com.example.Model.MenuSizeDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuSizeRepository extends JpaRepository<MenuSizeDB, Long> {
    List<MenuSizeDB> findByMenuId(Long menuId);
}
