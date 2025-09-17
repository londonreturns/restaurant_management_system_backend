package com.example.Repository;

import com.example.Model.MenuOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOptionDB, Long> {
    List<MenuOptionDB> findByMenuId(Long menuId);

    void deleteByMenuIdAndOptionId(Long menuId, Long optionId);;

    MenuOptionDB findAllByMenuIdAndOptionId(Long id, Long id1);

    List<MenuOptionDB> findAllByMenuId(Long menuId);
}
