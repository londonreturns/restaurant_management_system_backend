package com.example.Utils;

import com.example.Entity.MenuEntity;
import com.example.Exception.ResourceNotFoundException;
import com.example.Exception.ValidationException;
import com.example.Repository.MenuRepository;

public class MenuUtils {


    public static MenuEntity updateMenuDetails(MenuEntity oldMenuEntity, MenuEntity newMenuEntity) {
        oldMenuEntity.setMenuId(oldMenuEntity.getMenuId());
        oldMenuEntity.setMenuName(newMenuEntity.getMenuName());
        oldMenuEntity.setMenuDescription(newMenuEntity.getMenuDescription());
        oldMenuEntity.setMenuAvailability(newMenuEntity.getMenuAvailability());
        return oldMenuEntity;
    }

    public static MenuEntity checkIfMenuExists(MenuRepository menuRepository, Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu does not exists with the given id " + menuId));
    }

    public static void validateDetails(MenuEntity menuEntity) {
        if (menuEntity == null) {
            throw new ValidationException("MenuEntity cannot be null");
        }

        if (menuEntity.getMenuName() == null || menuEntity.getMenuName().trim().isEmpty()) {
            throw new ValidationException("Menu name is required and cannot be empty");
        }

        if (menuEntity.getMenuDescription() != null && menuEntity.getMenuDescription().length() > 255) {
            throw new ValidationException("Description should not exceed 255 characters");
        }

        if (menuEntity.getMenuAvailability() != null && menuEntity.getMenuAvailability().length() > 255) {
            throw new ValidationException("Invalid availability status. Allowed values are 'available' or 'unavailable'");
        }
    }

}
