package com.example.Repository;

import com.example.Dto.MenuDTO;
import com.example.Model.MenuDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<MenuDB, Long> {

    @Query("SELECT DISTINCT new com.example.Dto.MenuDTO(m.id, m.name, m.basePrice, m.categoryId, m.sizeGroupId)" +
            " FROM com.example.Model.MenuDB m" +
            " WHERE m.id = :menuId")
    MenuDTO getMenuDTOById(@Param("menuId") Long menuId);
}
