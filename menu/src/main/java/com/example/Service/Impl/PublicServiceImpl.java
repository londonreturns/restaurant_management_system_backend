package com.example.Service.Impl;

import com.example.Dto.MenuDTO;
import com.example.Dto.OptionDTO;
import com.example.Dto.OptionGroupDTO;
import com.example.Dto.SizeDTO;
import com.example.Exception.ResourceNotFoundException;
import com.example.Model.*;
import com.example.Repository.*;
import com.example.Service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
