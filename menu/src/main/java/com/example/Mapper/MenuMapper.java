package com.example.Mapper;

import com.example.Dto.MenuRequestDto;
import com.example.Dto.MenuResponseDto;
import com.example.Entity.MenuEntity;

public class MenuMapper {
    public static MenuResponseDto mapToMenuDto(MenuEntity menuEntity) {
        return new MenuResponseDto(
                menuEntity.getMenuId(),
                menuEntity.getMenuName(),
                menuEntity.getMenuDescription(),
                menuEntity.getMenuAvailability()
        );
    }

    public static MenuEntity mapToMenuEntity(MenuRequestDto menuRequestDto) {
        return new MenuEntity(
                menuRequestDto.getMenuId(),
                menuRequestDto.getName(),
                menuRequestDto.getDescription(),
                menuRequestDto.getAvailability()
        );
    }
}
