package com.example.Service;

import com.example.Dto.*;

import java.util.List;

public interface FoodService {
    SizeGroupDTO createSizeGroupAndSize(SizeGroupDTO sizeGroupDTO);

    List<SizeGroupDTO> getAllSizeGroups();

    SizeGroupDTO getSizeGroupById(Long sizeGroupId);

    SizeGroupDTO updateSizeGroupAndSize(SizeGroupDTO sizeGroupDTO);

    MenuDTO createMenuAndSize(MenuDTO menuDTO);

    MenuDTO updateMenuAndSize(MenuDTO menuDTO);

    List<CategoryDTO> getAllCategory();

    OptionGroupDTO createOptionGroupAndOption(OptionGroupDTO optionGroupDTO);

    OptionGroupDTO updateOptionGroupAndOption(OptionGroupDTO optionGroupDTO);

    SizeGroupOptionGroupDTO linkSizeGroupAndOptionGroup(SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO);

    MenuDTO handleMenuOptions(MenuDTO menuDTO);

    List<MenuDTO> getMenuOptions();

    MenuDTO getMenuOptionsDetailed(Long menuId);

    MenuDTO handleMenuOptionsDetailed(MenuDTO menuDTO);
}
