package com.example.Service;

import com.example.Dto.*;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface FoodService {
    SizeGroupDTO createSizeGroupAndSize(SizeGroupDTO sizeGroupDTO) throws ValidationException;

    List<SizeGroupDTO> getAllSizeGroups();

    SizeGroupDTO getSizeGroupById(Long sizeGroupId);

    SizeGroupDTO updateSizeGroupAndSize(SizeGroupDTO sizeGroupDTO) throws ValidationException;

    String deleteSizeGroupAndSizeById(Long sizeGroupId);

    MenuDTO createMenuAndSize(MenuDTO menuDTO);

    List<MenuDTO> getAllMenuAndMenuSizes();

    MenuDTO getMenuAndMenuSizesByMenuId(Long menuId);

    MenuDTO updateMenuAndSize(MenuDTO menuDTO);

    List<CategoryDTO> getAllCategory();

    OptionGroupDTO createOptionGroupAndOption(OptionGroupDTO optionGroupDTO);

    List<OptionGroupDTO> getAllOptionGroupAndOption();

    OptionGroupDTO getOptionGroupAndOptionById(Long optionGroupId);

    OptionGroupDTO updateOptionGroupAndOption(OptionGroupDTO optionGroupDTO);

    SizeGroupOptionGroupDTO linkSizeGroupAndOptionGroup(SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO);

    MenuDTO handleMenuOptions(MenuDTO menuDTO);

    List<MenuDTO> getMenuOptions();

    MenuDTO getMenuOptionsDetailed(Long menuId);

    MenuDTO handleMenuOptionsDetailed(MenuDTO menuDTO);
}
