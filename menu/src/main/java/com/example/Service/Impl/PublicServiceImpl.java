package com.example.Service.Impl;

import com.example.Dto.*;
import com.example.Exception.ResourceNotFoundException;
import com.example.Model.*;
import com.example.Repository.*;
import com.example.Service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuOptionRepository menuOptionRepository;

    @Autowired
    OptionGroupRepository optionGroupRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    private MenuSizeRepository menuSizeRepository;

    @Autowired
    private SizeGroupOptionGroupRepository sizeGroupOptionGroupRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeOptionRepository sizeOptionRepository;

    @Autowired
    private MenuOptionGroupRepository menuOptionGroupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public MenuDTO menuInfo(Long menuId) {
        MenuDTO menuDTO = new MenuDTO();

        MenuDB menuDB = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        List<SizeDB> sizeDBS = sizeRepository.findBySizeGroupId(menuDB.getSizeGroupId());

        List<SizeDTO> sizeDTOS = new ArrayList<>();

        for (SizeDB sizeDB : sizeDBS) {
            SizeDTO sizeDTO = new SizeDTO();

            MenuSizeDB menuSizeDB = menuSizeRepository.findByMenuIdAndSizeId(menuDB.getId(), sizeDB.getId());

            sizeDTO.setId(sizeDB.getId());
            sizeDTO.setName(sizeDB.getName());
            sizeDTO.setPrice(menuSizeDB.getPrice());
            sizeDTO.setSizeGroupId(menuDB.getSizeGroupId());

            sizeDTOS.add(sizeDTO);
        }

        List<MenuOptionDB> menuOptionDBs = menuOptionRepository.findByMenuId(menuId);

        List<OptionDTO> chosenOptionDTOs = new ArrayList<>();

        for (MenuOptionDB menuOptionDB : menuOptionDBs) {
            OptionDTO optionDTO = new OptionDTO();

            optionDTO.setOptionId(menuOptionDB.getOptionId());
            optionDTO.setName(menuOptionDB.getOption().getName());

            chosenOptionDTOs.add(optionDTO);
        }

        List<SizeGroupOptionGroupDB> sizeGroupOptionGroupDBS = sizeGroupOptionGroupRepository.findAllBySizeGroupId(menuDB.getSizeGroupId());

        List<OptionGroupDTO> availableOptionDTOS = new ArrayList<>();

        for (SizeGroupOptionGroupDB sizeGroupOptionGroupDB : sizeGroupOptionGroupDBS) {
            OptionGroupDB optionGroupDB = optionGroupRepository.findById(sizeGroupOptionGroupDB.getOptionGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("OptionGroup with id " + sizeGroupOptionGroupDB.getOptionGroupId() + " not found"));

            OptionGroupDTO optionGroupDTO = new OptionGroupDTO();
            optionGroupDTO.setId(optionGroupDB.getId());
            optionGroupDTO.setName(optionGroupDB.getName());

            List<OptionDTO> optionDTOS = new ArrayList<>();

            List<OptionDB> optionDBS = optionRepository.findByOptionGroupId(optionGroupDB.getId());

            for (OptionDB optionDB : optionDBS) {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setId(optionDB.getId());
                optionDTO.setName(optionDB.getName());

                optionDTOS.add(optionDTO);
            }
            optionGroupDTO.setOptions(optionDTOS);
            availableOptionDTOS.add(optionGroupDTO);
        }

        menuDTO.setId(menuId);
        menuDTO.setName(menuDB.getName());
        menuDTO.setBasePrice(menuDB.getBasePrice());

        menuDTO.setSizes(sizeDTOS);
        menuDTO.setMenuOptions(chosenOptionDTOs);

        menuDTO.setOptionGroups(availableOptionDTOS);

        return menuDTO;
    }

    @Override
    public MenuDTO menuInfoBetter(Long menuId) {

        MenuDTO menuDTO = menuRepository.getMenuDTOById(menuId);
        List<SizeDTO> sizeDTOS = sizeRepository.getBySizeGroupId(menuDTO.getSizeGroupId());
        List<OptionDTO> menuOptions = menuSizeRepository.findDTOByMenuId(menuId);

        List<OptionGroupDTO> optionGroups = optionGroupRepository.findAllDTOsWithOptions();
        List<SizeOptionDTO> sizeOfOptionsDTOS = sizeOptionRepository.findAllSizeOptions();

        Map<Long, OptionGroupDTO> optionGroupDTOMap = optionGroups.stream()
                .collect(Collectors.toMap(OptionGroupDTO::getId, group -> group));


        List<OptionDTO> optionDTOs = optionRepository.findDTOSByIds(optionGroupDTOMap.keySet());

        Map<Long, List<SizeOptionDTO>> sizeOptionsByOptionId = sizeOfOptionsDTOS.stream()
                .collect(Collectors.groupingBy(SizeOptionDTO::getOptionId));

        List<OptionGroupDTO> selectedOptionGroupDTOS = menuOptionGroupRepository.findOptionGroupDTOByMenuId(menuId);

        Set<Long> selectedOptionGroupIds = selectedOptionGroupDTOS.stream()
                .map(OptionGroupDTO::getId)
                .collect(Collectors.toSet());

        for (OptionDTO optionDTO : optionDTOs) {
            Long optionGroupId = optionDTO.getOptionGroupId();
            OptionGroupDTO optionGroupDTO = optionGroupDTOMap.get(optionGroupId);

            if (optionGroupDTO != null) {
                if (optionGroupDTO.getOptions() == null) {
                    optionGroupDTO.setOptions(new ArrayList<>());
                }

                List<SizeOptionDTO> sizesForOption;
                if (sizeOptionsByOptionId.containsKey(optionDTO.getId())) {
                    sizesForOption = sizeOptionsByOptionId.get(optionDTO.getId());
                } else {
                    sizesForOption = new ArrayList<>();
                }

                Map<Long, Float> priceBySizeId = sizesForOption.stream()
                        .collect(Collectors.toMap(
                                SizeOptionDTO::getSizeId,
                                SizeOptionDTO::getPrice
                        ));

                List<SizeDTO> sizesWithPrice = new ArrayList<>();
                for (SizeDTO sizeDTO : sizeDTOS) {
                    SizeDTO size = new SizeDTO();
                    size.setId(sizeDTO.getId());
                    size.setName(sizeDTO.getName());
                    size.setPrice(priceBySizeId.getOrDefault(sizeDTO.getId(), 0f));
                    sizesWithPrice.add(size);
                }

                optionDTO.setSizes(sizesWithPrice);
                optionGroupDTO.getOptions().add(optionDTO);
                optionGroupDTO.setSelected(selectedOptionGroupIds.contains(optionGroupDTO.getId()));
            }
        }

        for (OptionDTO menuOptionDTO : menuOptions) {
            List<SizeOptionDTO> sizesForOption;
            if (sizeOptionsByOptionId.containsKey(menuOptionDTO.getId())) {
                sizesForOption = sizeOptionsByOptionId.get(menuOptionDTO.getId());
            } else {
                sizesForOption = new ArrayList<>();
            }

            Map<Long, Float> priceBySizeId = sizesForOption.stream()
                    .collect(Collectors.toMap(
                            SizeOptionDTO::getSizeId,
                            SizeOptionDTO::getPrice
                    ));

            List<SizeDTO> sizesWithPrice = new ArrayList<>();
            for (SizeDTO sizeDTO : sizeDTOS) {
                SizeDTO size = new SizeDTO();
                size.setId(sizeDTO.getId());
                size.setName(sizeDTO.getName());
                size.setPrice(priceBySizeId.getOrDefault(sizeDTO.getId(), 0f));
                sizesWithPrice.add(size);
            }

            menuOptionDTO.setSizes(sizesWithPrice);
        }

        menuDTO.setSizes(sizeDTOS);
        menuDTO.setMenuOptions(menuOptions);
        menuDTO.setOptionGroups(optionGroups);

        return menuDTO;
    }


    @Override
    public SizeGroupOptionGroupDTO extraPrices(Long sizeGroupOptionGroupId) {

        SizeGroupOptionGroupDB sizeGroupOptionGroupDB = sizeGroupOptionGroupRepository.findById(sizeGroupOptionGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("OptionGroup with id " + sizeGroupOptionGroupId + " not found"));

        Long sizeGroupId = sizeGroupOptionGroupDB.getSizeGroupId();

        List<OptionDTO> optionDTOS = sizeOptionRepository.findBySizeGroupOptionGroupId(sizeGroupId);

        if (optionDTOS != null) {
            List<SizeOptionDTO> allSizeOptions = sizeOptionRepository.findAllSizeOptions();
            List<SizeDTO> sizeDTOS = sizeRepository.getBySizeGroupId(sizeGroupId);

            for (OptionDTO optionDTO : optionDTOS) {

                if (sizeDTOS != null && !sizeDTOS.isEmpty()) {
                    List<SizeDTO> finalSizeDTOs = new ArrayList<>();

                    for (SizeDTO sizeDTO : sizeDTOS) {
                        Long sizeId = sizeDTO.getId();
                        Long optionId = optionDTO.getId();

                        SizeDTO enrichedSize = new SizeDTO();
                        enrichedSize.setId(sizeId);
                        enrichedSize.setName(optionDTO.getName());


                        for (SizeOptionDTO sizeOptionDTO : allSizeOptions) {
                            if (sizeOptionDTO.getSizeId().equals(sizeId) &&
                                    sizeOptionDTO.getOptionId().equals(optionId)) {
                                enrichedSize.setPrice(sizeOptionDTO.getPrice());
                                break;
                            }
                        }

                        finalSizeDTOs.add(enrichedSize);
                    }

                    optionDTO.setSizes(finalSizeDTOs);
                }
            }
        }

        SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO = new SizeGroupOptionGroupDTO();
        sizeGroupOptionGroupDTO.setOptions(optionDTOS);
        return sizeGroupOptionGroupDTO;
    }


    @Override
    public SizeGroupOptionGroupDTO handleExtraPrices(SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO) {

        SizeGroupOptionGroupDB sizeGroupOptionGroupDB = sizeGroupOptionGroupRepository.findById(sizeGroupOptionGroupDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("SizeGroupOptionGroup with id " + sizeGroupOptionGroupDTO.getId() + " not found"));

        List<Long> optionIds = sizeGroupOptionGroupDTO.getOptions().stream()
                .map(OptionDTO::getId)
                .collect(Collectors.toList());

        List<OptionDB> optionDBs = optionRepository.findAllById(optionIds);
        Map<Long, OptionDB> optionDBMap = optionDBs.stream()
                .collect(Collectors.toMap(OptionDB::getId, Function.identity()));

        List<SizeDB> sizeDBs = sizeRepository.findBySizeGroupId(sizeGroupOptionGroupDB.getSizeGroupId());
        Map<Long, SizeDB> sizeDBMap = sizeDBs.stream()
                .collect(Collectors.toMap(SizeDB::getId, Function.identity()));

        List<SizeOptionDB> sizeOptionDBsToSave = new ArrayList<>();

        for (OptionDTO optionDTO : sizeGroupOptionGroupDTO.getOptions()) {
            OptionDB optionDB = optionDBMap.get(optionDTO.getId());
            if (optionDB == null) {
                throw new ResourceNotFoundException("OptionDB with id " + optionDTO.getId() + " not found");
            }

            for (SizeDTO sizeDTO : optionDTO.getSizes()) {
                SizeDB sizeDB = sizeDBMap.get(sizeDTO.getId());
                if (sizeDB == null) {
                    throw new ResourceNotFoundException("SizeDB with id " + sizeDTO.getId() + " not found");
                }

                SizeOptionDB sizeOptionDB = new SizeOptionDB();

                sizeOptionDB.setOptionId(optionDB.getId());
                sizeOptionDB.setSizeId(sizeDB.getId());
                sizeOptionDB.setOption(optionDB);
                sizeOptionDB.setSize(sizeDB);
                sizeOptionDB.setSizeGroupOptionGroupId(sizeGroupOptionGroupDB.getSizeGroupId());
                sizeOptionDB.setSizeGroupOptionGroup(sizeGroupOptionGroupDB);
                sizeOptionDB.setPrice(sizeDTO.getPrice());

                sizeOptionDBsToSave.add(sizeOptionDB);
            }
        }

        sizeOptionRepository.saveAll(sizeOptionDBsToSave);

        return sizeGroupOptionGroupDTO;
    }

    @Override
    public MenuDTO getMenuOptionGroup(Long menuId) {
        MenuDTO menuDTO = menuRepository.getMenuDTOById(menuId);

        List<OptionGroupDTO> selectedOptionGroupDTOS = menuOptionGroupRepository.findOptionGroupDTOByMenuId(menuId);

        List<OptionGroupDTO> allOptionGroupDTOS = optionGroupRepository.findAllDTOsWithOptions();

        Set<Long> selectedOptionGroupIds = selectedOptionGroupDTOS.stream()
                .map(OptionGroupDTO::getId)
                .collect(Collectors.toSet());

        List<OptionGroupDTO> optionGroupDTOS = new ArrayList<>();

        for (OptionGroupDTO optionGroupDTO : allOptionGroupDTOS) {
            OptionGroupDTO optionGroup = new OptionGroupDTO();
            optionGroup.setId(optionGroupDTO.getId());
            optionGroup.setName(optionGroupDTO.getName());
            optionGroup.setOptions(optionGroupDTO.getOptions());
            optionGroup.setSelected(selectedOptionGroupIds.contains(optionGroupDTO.getId()));
            optionGroupDTOS.add(optionGroup);
        }

        menuDTO.setOptionGroups(optionGroupDTOS);
        return menuDTO;
    }


    @Override
    public MenuDTO handleMenuOptionGroup(MenuDTO menuDTO) {
        List<OptionGroupDTO> optionGroupDTOS = menuDTO.getOptionGroups();
        List<Long> inputOptionGroupIds = optionGroupDTOS.stream()
                .map(OptionGroupDTO::getId)
                .collect(Collectors.toList());

        MenuDB menuDB = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu with id " + menuDTO.getId() + " not found"));

        List<OptionGroupDB> optionGroupDBS = optionGroupRepository.findAllById(inputOptionGroupIds);

        Map<Long, OptionGroupDB> optionGroupDBMap = optionGroupDBS.stream()
                .collect(Collectors.toMap(OptionGroupDB::getId, Function.identity()));

        List<MenuOptionGroupDB> existingMenuOptionGroups = menuOptionGroupRepository.findByMenuId(menuDB.getId());
        Map<Long, MenuOptionGroupDB> existingMap  = existingMenuOptionGroups.stream()
                .collect(Collectors.toMap(MenuOptionGroupDB::getOptionGroupId, relation -> relation));


        for (OptionGroupDTO dto : optionGroupDTOS) {
            Long optionGroupId = dto.getId();
            boolean selected = dto.isSelected();
            boolean existsInDb = existingMap.containsKey(optionGroupId);

            if (selected && !existsInDb) {
                OptionGroupDB optionGroupDB = optionGroupDBMap.get(optionGroupId);

                MenuOptionGroupDB newRelation = new MenuOptionGroupDB();
                newRelation.setMenu(menuDB);
                newRelation.setOptionGroup(optionGroupDB);

                menuOptionGroupRepository.save(newRelation);

            } else if (!selected && existsInDb) {
                MenuOptionGroupDB relationToRemove = existingMap.get(optionGroupId);
                menuOptionGroupRepository.delete(relationToRemove);
            }
        }

        return menuDTO;
    }

    @Override
    public ResponseDTO categoriesWithMenu() {
        List<CategoryDTO> categoryDTOS = categoryRepository.getAllCategories();
        List<MenuDTO> menuDTOS = menuRepository.getAllMenus();

        for (CategoryDTO categoryDTO : categoryDTOS) {
            List<MenuDTO> categoryMenus = new ArrayList<>();

            for (MenuDTO menuDTO : menuDTOS) {
                if (menuDTO.getCategoryId().equals(categoryDTO.getId())) {
                    categoryMenus.add(menuDTO);
                }
            }
            categoryDTO.setMenu(categoryMenus);
        }

        return new ResponseDTO(categoryDTOS);
    }

}