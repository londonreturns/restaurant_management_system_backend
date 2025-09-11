package com.example.Service.Impl;

import com.example.Dto.MenuRequestDto;
import com.example.Dto.MenuResponseDto;
import com.example.Entity.MenuEntity;
import com.example.Mapper.MenuMapper;
import com.example.Repository.MenuRepository;
import com.example.Service.MenuService;
import com.example.Utils.MenuUtils;
import com.example.resource.ResourceNotFoundException;
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
        MenuEntity menu = menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu does not exists with the given id " + menuId));
        return MenuMapper.mapToMenuDto(menu);
    }

    @Override
    public MenuResponseDto createMenu(MenuRequestDto menuRequestDto) {
        MenuEntity menuEntity = MenuMapper.mapToMenuEntity(menuRequestDto);
        menuEntity = menuRepository.save(menuEntity);
        return MenuMapper.mapToMenuDto(menuEntity);
    }

    @Override
    public void deleteMenu(Long menuId) {
        menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu does not exists with the given id " + menuId));

        menuRepository.deleteById(menuId);
    }

    @Override
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto updateMenuRequestDto) {
        MenuEntity oldMenuEntity = menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu does not exists with the given id " + menuId));

        MenuEntity newMenuEntity = MenuMapper.mapToMenuEntity(updateMenuRequestDto);

        MenuEntity updatedMenuDetails = MenuUtils.updateMenuDetails(oldMenuEntity, newMenuEntity);
        menuRepository.save(updatedMenuDetails);
        return MenuMapper.mapToMenuDto(updatedMenuDetails);
    }
}
