package com.example.Repository;

import com.example.Dto.OptionDTO;
import com.example.Model.MenuSizeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuSizeRepository extends JpaRepository<MenuSizeDB, Long> {
    List<MenuSizeDB> findByMenuId(Long menuId);

    MenuSizeDB findByMenuIdAndSizeId(Long id, Long id1);

    @Query("SELECT DISTINCT new com.example.Dto.OptionDTO(o.id, o.name)" +
            " FROM menu_option mo" +
            " JOIN option o" +
            " ON mo.optionId = o.id" +
            " WHERE mo.menuId = :menuId")
    List<OptionDTO> findDTOByMenuId(@Param("menuId") Long menuId);
}
