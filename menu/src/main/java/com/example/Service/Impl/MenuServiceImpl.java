package com.example.Service.Impl;

import com.example.Dto.MenuRequestDto;
import com.example.Dto.MenuResponseDto;
import com.example.Entity.MenuEntity;
import com.example.Mapper.MenuMapper;
import com.example.Repository.MenuRepository;
import com.example.Service.MenuService;
import com.example.Utils.MenuUtils;
import com.example.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Override
    public List<MenuResponseDto> getAllMenuDetails() {
        List<MenuEntity> menus = menuRepository.findAll();
        return menus.stream()
                .map(MenuMapper::mapToMenuDto)
                .collect(Collectors.toList());
    }

    @Override
    public MenuResponseDto getMenuDetailsById(Long menuId) {
        MenuEntity menuEntity = MenuUtils.checkIfMenuExists(menuRepository, menuId);
        return MenuMapper.mapToMenuDto(menuEntity);
    }

    @Override
    public MenuResponseDto createMenu(MenuRequestDto menuRequestDto) {
        MenuEntity menuEntity = MenuMapper.mapToMenuEntity(menuRequestDto);
        MenuUtils.validateDetails(menuEntity);
        MenuEntity savedMenuEntity = menuRepository.save(menuEntity);
        return MenuMapper.mapToMenuDto(savedMenuEntity);
    }

    @Override
    public void deleteMenu(Long menuId) {
        MenuUtils.checkIfMenuExists(menuRepository, menuId);
        menuRepository.deleteById(menuId);
    }

    @Override
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto updateMenuRequestDto) {
        MenuEntity oldMenuEntity = MenuUtils.checkIfMenuExists(menuRepository, menuId);
        MenuEntity newMenuEntity = MenuMapper.mapToMenuEntity(updateMenuRequestDto);
        MenuUtils.validateDetails(newMenuEntity);
        MenuEntity updatedMenuDetails = MenuUtils.updateMenuDetails(oldMenuEntity, newMenuEntity);
        menuRepository.save(updatedMenuDetails);
        return MenuMapper.mapToMenuDto(updatedMenuDetails);
    }
}
