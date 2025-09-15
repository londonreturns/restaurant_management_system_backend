package com.example.Repository;

import com.example.Model.MenuDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuDB, Long> {
}
