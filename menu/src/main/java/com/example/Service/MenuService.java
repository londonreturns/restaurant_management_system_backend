package com.example.Service;


import com.example.Dto.MenuRequestDto;
import com.example.Dto.MenuResponseDto;

import java.util.List;

public interface MenuService {
    List<MenuResponseDto> getAllMenuDetails();

    MenuResponseDto getMenuDetailsById(Long menuId);

    MenuResponseDto createMenu(MenuRequestDto menuRequestDto);

    void deleteMenu(Long menuId);

    MenuResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto);
}
