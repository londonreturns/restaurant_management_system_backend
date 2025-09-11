package com.example.Utils;

import com.example.Entity.MenuEntity;

public class MenuUtils {
    public static MenuEntity updateMenuDetails(MenuEntity oldMenuEntity, MenuEntity newMenuEntity) {
        oldMenuEntity.setMenuId(oldMenuEntity.getMenuId());
        oldMenuEntity.setName(newMenuEntity.getName());
        oldMenuEntity.setDescription(newMenuEntity.getDescription());
        oldMenuEntity.setAvailability(newMenuEntity.getAvailability());
        return oldMenuEntity;
    }
}
