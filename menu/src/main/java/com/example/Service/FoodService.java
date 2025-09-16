package com.example.Service;

import com.example.Dto.CategoryDTO;
import com.example.Dto.MenuDTO;
import com.example.Dto.OptionGroupDTO;
import com.example.Dto.SizeGroupDTO;

import java.util.List;

public interface FoodService {
    SizeGroupDTO createSizeGroupAndSize(SizeGroupDTO sizeGroupDTO);

    SizeGroupDTO updateSizeGroupAndSize(SizeGroupDTO sizeGroupDTO);

    MenuDTO createMenuAndSize(MenuDTO menuDTO);

    MenuDTO updateMenuAndSize(MenuDTO menuDTO);

    List<MenuDTO> getAllMenu();

    List<CategoryDTO> getAllCategory();

    OptionGroupDTO createOptionGroupAndOption(OptionGroupDTO optionGroupDTO);

    OptionGroupDTO updateOptionGroupAndOption(OptionGroupDTO optionGroupDTO);
}
