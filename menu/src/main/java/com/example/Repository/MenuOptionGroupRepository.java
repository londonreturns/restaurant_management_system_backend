package com.example.Repository;

import com.example.Dto.OptionGroupDTO;
import com.example.Model.MenuOptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuOptionGroupRepository extends JpaRepository<MenuOptionGroupDB, Long> {
    List<MenuOptionGroupDB> findByMenuId(Long menuId);

    @Query("SELECT new com.example.Dto.OptionGroupDTO(og.id, og.name)" +
            " FROM menu_option_group mog" +
            " JOIN option_group og ON mog.optionGroupId = og.id" +
            " WHERE mog.menuId = :menuId")
    List<OptionGroupDTO> findOptionGroupDTOByMenuId(@Param("menuId") Long menuId);
}
