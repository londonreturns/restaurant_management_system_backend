package com.example.Service.Impl;

import com.example.Dto.*;
import com.example.Exception.ResourceNotFoundException;
import com.example.Model.*;
import com.example.Repository.*;
import com.example.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeGroupRepository sizeGroupRepository;

    @Autowired
    private MenuSizeRepository menuSizeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private SizeGroupOptionGroupRepository sizeGroupOptionGroupRepository;

    @Autowired
    private MenuOptionRepository menuOptionRepository;

    @Override
    public SizeGroupDTO createSizeGroupAndSize(SizeGroupDTO sizeGroupDTO) {
        SizeGroupDB sizeGroup = new SizeGroupDB();
        sizeGroup.setName(sizeGroupDTO.getName());
        SizeGroupDB sizeGroupDB = sizeGroupRepository.save(sizeGroup);

        List<SizeDTO> updatedSizes = new ArrayList<>();

        for (SizeDTO sizeDTO : sizeGroupDTO.getSizes()) {
            SizeDB size = new SizeDB();
            size.setName(sizeDTO.getName());
            size.setSizeGroup(sizeGroup);

            sizeRepository.save(size);

            SizeDTO updatedSizeDTO = new SizeDTO();
            updatedSizeDTO.setId(size.getId());
            updatedSizeDTO.setName(size.getName());
            updatedSizeDTO.setSizeGroupId(sizeGroupDB.getId());

            updatedSizes.add(updatedSizeDTO);
        }

        sizeGroupDTO.setSizes(updatedSizes);
        sizeGroupDTO.setId(sizeGroup.getId());

        return sizeGroupDTO;
    }


    @Override
    public SizeGroupDTO updateSizeGroupAndSize(SizeGroupDTO sizeGroupDTO) {
        Long sizeGroupId = sizeGroupDTO.getId();

        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(sizeGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Size group with id " + sizeGroupId + " does not exist"));

        sizeGroupDB.setName(sizeGroupDTO.getName());

        List<SizeDB> updatedSizes = new ArrayList<>();

        for (SizeDTO sizeDTO : sizeGroupDTO.getSizes()) {
            SizeDB existingSize = sizeRepository.findById(sizeDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Size with id " + sizeDTO.getId() + " does not exist"));

            existingSize.setName(sizeDTO.getName());

            existingSize.setSizeGroupId(sizeGroupId);

            updatedSizes.add(existingSize);
        }

        sizeRepository.saveAll(updatedSizes);

        sizeGroupDB.setSizes(updatedSizes);
        sizeGroupRepository.save(sizeGroupDB);

        SizeGroupDTO updatedSizeGroupDTO = new SizeGroupDTO();
        updatedSizeGroupDTO.setId(sizeGroupId);
        updatedSizeGroupDTO.setName(sizeGroupDTO.getName());

        updatedSizeGroupDTO.setSizes(updatedSizes.stream()
                .map(size -> new SizeDTO(size.getId(), size.getName(), size.getSizeGroupId()))
                .collect(Collectors.toList()));

        return updatedSizeGroupDTO;
    }


    @Override
    public MenuDTO createMenuAndSize(MenuDTO menuDTO) {
        MenuDB menu = new MenuDB();
        menu.setName(menuDTO.getName());
        menu.setBasePrice(menuDTO.getBasePrice());

        CategoryDB category = categoryRepository.findById(menuDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        menu.setCategory(category);

        menuRepository.save(menu);

        Long sizeGroupId = menuDTO.getSizeGroupId();

        SizeGroupDB sizeGroup = sizeGroupRepository.findById(sizeGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("SizeGroup not found"));

        List<MenuSizeDB> menuSizeDBList = new ArrayList<>();
        for (SizeDB size : sizeGroup.getSizes()) {
            MenuSizeDB menuSizeDB = new MenuSizeDB();
            menuSizeDB.setPrice(menuDTO.getBasePrice());
            menuSizeDB.setMenu(menu);
            menuSizeDB.setSize(size);

            menuSizeDBList.add(menuSizeDB);
        }

        menuSizeRepository.saveAll(menuSizeDBList);

        List<MenuSizeDTO> menuSizeDTOs = new ArrayList<>();
        for (MenuSizeDB menuSizeDB : menuSizeDBList) {
            MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
            menuSizeDTO.setId(menuSizeDB.getId());
            menuSizeDTO.setPrice(menuSizeDB.getPrice());
            menuSizeDTO.setMenuId(menuSizeDB.getMenu().getId());
            menuSizeDTO.setSizeId(menuSizeDB.getSize().getId());
            menuSizeDTOs.add(menuSizeDTO);
        }

        menuDTO.setId(menu.getId());
        menuDTO.setSizeGroupId(sizeGroupId);
        menuDTO.setMenuSizes(menuSizeDTOs);

        return menuDTO;
    }

    @Override
    public MenuDTO updateMenuAndSize(MenuDTO menuDTO) {
        Long menuId = menuDTO.getId();
        MenuDB existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

        existingMenu.setName(menuDTO.getName());
        existingMenu.setBasePrice(menuDTO.getBasePrice());

        CategoryDB category = categoryRepository.findById(menuDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingMenu.setCategory(category);

        Long sizeGroupId = menuDTO.getSizeGroupId();

        List<MenuSizeDB> updatedMenuSizeDBList = new ArrayList<>();

        for (MenuSizeDTO menuSizeDTO : menuDTO.getMenuSizes()) {
            MenuSizeDB existingMenuSize = menuSizeRepository.findById(menuSizeDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("MenuSize not found for id " + menuSizeDTO.getId()));

            existingMenuSize.setPrice(menuSizeDTO.getPrice());

            SizeDB size = sizeRepository.findById(menuSizeDTO.getSizeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Size not found for id " + menuSizeDTO.getSizeId()));
            existingMenuSize.setSize(size);
            existingMenuSize.setMenu(existingMenu);

            updatedMenuSizeDBList.add(existingMenuSize);
        }

        menuSizeRepository.saveAll(updatedMenuSizeDBList);

        menuRepository.save(existingMenu);

        List<MenuSizeDTO> updatedMenuSizeDTOs = updatedMenuSizeDBList.stream()
                .map(menuSizeDB -> new MenuSizeDTO(
                        menuSizeDB.getId(),
                        menuSizeDB.getPrice(),
                        menuSizeDB.getMenu().getId(),
                        menuSizeDB.getSize().getId()))
                .collect(Collectors.toList());

        menuDTO.setName(existingMenu.getName());
        menuDTO.setBasePrice(existingMenu.getBasePrice());
        menuDTO.setCategoryId(existingMenu.getCategoryId());
        menuDTO.setSizeGroupId(sizeGroupId);
        menuDTO.setMenuSizes(updatedMenuSizeDTOs);

        return menuDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDB> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (CategoryDB category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());

            List<MenuDB> menus = menuRepository.findByCategoryId(category.getId());
            List<MenuDTO> menuDTOList = new ArrayList<>();

            for (MenuDB menu : menus) {
                MenuDTO menuDTO = new MenuDTO();
                menuDTO.setId(menu.getId());
                menuDTO.setName(menu.getName());
                menuDTO.setBasePrice(menu.getBasePrice());
                menuDTO.setCategoryId(category.getId());

                List<MenuSizeDB> menuSizeDBs = menuSizeRepository.findByMenuId(menu.getId());
                List<MenuSizeDTO> menuSizeDTOList = new ArrayList<>();

                for (MenuSizeDB menuSizeDB : menuSizeDBs) {
                    MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
                    menuSizeDTO.setId(menuSizeDB.getId());
                    menuSizeDTO.setPrice(menuSizeDB.getPrice());
                    menuSizeDTO.setMenuId(menuSizeDB.getMenu().getId());
                    menuSizeDTO.setSizeId(menuSizeDB.getSize().getId());
                    menuSizeDTOList.add(menuSizeDTO);
                }

                if (!menuSizeDBs.isEmpty()) {
                    SizeDB firstSize = menuSizeDBs.get(0).getSize();
                    if (firstSize != null && firstSize.getSizeGroupId() != null) {
                        menuDTO.setSizeGroupId(firstSize.getSizeGroupId());
                    }
                }

                menuDTO.setMenuSizes(menuSizeDTOList);
                menuDTOList.add(menuDTO);
            }

            categoryDTO.setMenu(menuDTOList);
            categoryDTOList.add(categoryDTO);
        }

        return categoryDTOList;
    }


    public OptionGroupDTO createOptionGroupAndOption(OptionGroupDTO optionGroupDTO) {
        OptionGroupDB optionGroupDB = new OptionGroupDB();
        optionGroupDB.setName(optionGroupDTO.getName());

        OptionGroupDB savedOptionGroup = optionGroupRepository.save(optionGroupDB);

        optionGroupDTO.setId(savedOptionGroup.getId());

        for (OptionDTO optionDTO : optionGroupDTO.getOptions()) {
            OptionDB optionDB = new OptionDB();
            optionDB.setName(optionDTO.getName());
            optionDB.setOptionGroup(savedOptionGroup);

            OptionDB savedOption = optionRepository.save(optionDB);

            optionDTO.setId(savedOption.getId());
            optionDTO.setOptionGroupId(savedOptionGroup.getId());
        }

        return optionGroupDTO;
    }


    public OptionGroupDTO updateOptionGroupAndOption(OptionGroupDTO optionGroupDTO) {
        OptionGroupDB optionGroupDB = optionGroupRepository.findById(optionGroupDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Option group with id " + optionGroupDTO.getId() + " does not exist"));

        optionGroupDB.setName(optionGroupDTO.getName());

        optionGroupRepository.save(optionGroupDB);

        for (OptionDTO optionDTO : optionGroupDTO.getOptions()) {
            OptionDB optionDB = optionRepository.findById(optionDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Option with id " + optionDTO.getId() + " does not exist"));

            optionDB.setName(optionDTO.getName());
            optionDB.setOptionGroup(optionGroupDB);

            optionRepository.save(optionDB);

            optionDTO.setId(optionDB.getId());
        }

        return optionGroupDTO;
    }

    @Override
    public SizeGroupOptionGroupDTO linkSizeGroupAndOptionGroup(SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO) {
        Long sizeGroupId = sizeGroupOptionGroupDTO.getSizeGroupId();
        Long optionGroupId = sizeGroupOptionGroupDTO.getOptionGroupId();

        OptionGroupDB optionGroupDB = optionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Option group with id " + optionGroupId + " does not exist"));

        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(sizeGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Size group with id " + sizeGroupId + " does not exist"));

        SizeGroupOptionGroupDB sizeGroupOptionGroupDB = new SizeGroupOptionGroupDB();
        sizeGroupOptionGroupDB.setSizeGroup(sizeGroupDB);
        sizeGroupOptionGroupDB.setOptionGroup(optionGroupDB);
        sizeGroupOptionGroupDB.setSizeGroupId(sizeGroupId);
        sizeGroupOptionGroupDB.setOptionGroupId(optionGroupId);

        SizeGroupOptionGroupDB savedSizeGroupOptionGroupDB = sizeGroupOptionGroupRepository.save(sizeGroupOptionGroupDB);
        sizeGroupOptionGroupDTO.setId(savedSizeGroupOptionGroupDB.getId());

        return sizeGroupOptionGroupDTO;
    }

    @Transactional
    @Override
    public MenuDTO handleMenuOptions(MenuDTO menuDTO) {
        MenuDB menuDB = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

        for (OptionDTO optionDTO : menuDTO.getOptions()) {
            OptionDB optionDB = optionRepository.findById(optionDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Option with id " + optionDTO.getId() + " does not exist"));

            if (menuOptionRepository.findAllByMenuIdAndOptionId(menuDTO.getId(), optionDTO.getId()) == null) {
                MenuOptionDB menuOptionDB = new MenuOptionDB();
                menuOptionDB.setMenu(menuDB);
                menuOptionDB.setMenuId(menuDTO.getId());
                menuOptionDB.setOption(optionDB);
                menuOptionDB.setOptionId(optionDB.getId());

                menuOptionRepository.save(menuOptionDB);
            }

        }

        for (OptionDTO optionDTO : menuDTO.getRemovedOptions()) {
            optionRepository.findById(optionDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Option with id " + optionDTO.getId() + " does not exist"));

            menuOptionRepository.deleteByMenuIdAndOptionId(menuDTO.getId(), optionDTO.getId());
        }

        return menuDTO;
    }


    @Override
    public List<MenuDTO> getMenuOptions() {
        List<MenuDB> menuDBs = menuRepository.findAll();

        List<MenuDTO> menuDTOs = new ArrayList<>();

        for (MenuDB menuDB : menuDBs) {
            MenuDTO menuDTO = new MenuDTO();

            menuDTO.setId(menuDB.getId());
            menuDTO.setName(menuDB.getName());
            menuDTO.setBasePrice(menuDB.getBasePrice());
            menuDTO.setCategoryId(menuDB.getCategoryId());

            List<MenuOptionDB> menuOptionDBs = menuOptionRepository.findByMenuId(menuDB.getId());

            List<OptionDTO> optionDTOs = new ArrayList<>();
            for (MenuOptionDB menuOptionDB : menuOptionDBs) {
                OptionDTO optionDTO = new OptionDTO();

                optionDTO.setId(menuOptionDB.getOption().getId());
                optionDTO.setName(menuOptionDB.getOption().getName());
                optionDTO.setOptionGroupId(menuOptionDB.getOption().getOptionGroupId());

                optionDTOs.add(optionDTO);
            }

            menuDTO.setOptions(optionDTOs);
            menuDTOs.add(menuDTO);
        }

        return menuDTOs;
    }

    @Override
    public MenuDTO getMenuOptionsDetailed(Long menuId) {
        MenuDB menuDB = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu with id " + menuId + " not found"));

        List<SizeGroupOptionGroupDB> sizeGroupOptionGroupDBS = sizeGroupOptionGroupRepository.findAllBySizeGroupId(menuDB.getSizeGroupId());

        List<OptionDB> availableOptionDBS = new ArrayList<>();

        for (SizeGroupOptionGroupDB sizeGroupOptionGroupDB : sizeGroupOptionGroupDBS) {
            Long optionGroupId = sizeGroupOptionGroupDB.getOptionGroupId();

            List<OptionDB> optionDBS = optionRepository.findByOptionGroupId(optionGroupId);

            availableOptionDBS.addAll(optionDBS);

        }

        List<MenuOptionDB> chosenMenuOptions = menuOptionRepository.findAllByMenuId(menuId);

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menuId);

        List<OptionDTO> optionDTOs = new ArrayList<>();

        for (OptionDB availableOption : availableOptionDBS) {
            boolean isAvailable = false;
            for (MenuOptionDB chosenMenuOption : chosenMenuOptions) {
                if (availableOption.getId().equals(chosenMenuOption.getOptionId())) {
                    isAvailable = true;
                    break;
                }
            }
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(availableOption.getId());
            optionDTO.setName(availableOption.getName());
            optionDTO.setOptionGroupId(availableOption.getOptionGroupId());
            optionDTO.setSelected(isAvailable);

            optionDTOs.add(optionDTO);
        }

        menuDTO.setOptions(optionDTOs);
        return menuDTO;
    }
}