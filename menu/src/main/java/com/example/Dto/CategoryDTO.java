package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;

    private String name;

    private List<MenuDTO> menu;

    public CategoryDTO(Long id, String name, Long menuId, String menuName, Float menuBasePrice) {
        this.id = id;
        this.name = name;
        this.menu = new ArrayList<>();

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menuId);
        menuDTO.setName(menuName);
        menuDTO.setBasePrice(menuBasePrice);
        menuDTO.setCategoryId(id);

        this.menu.add(menuDTO);
    }

}
