package com.example.Service.Impl;

import com.example.Dto.*;
import com.example.Exception.ResourceNotFoundException;
import com.example.Model.*;
import com.example.Repository.*;
import com.example.Service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public SizeGroupOptionGroupDTO extraPrices(Long sizeGroupOptionGroupId) {

        SizeGroupOptionGroupDB sizeGroupOptionGroupDB = sizeGroupOptionGroupRepository.findById(sizeGroupOptionGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("OptionGroup with id " + sizeGroupOptionGroupId + " not found"));

        List<OptionDTO> optionDTOS = sizeOptionRepository.findBySizeGroupOptionGroupId(sizeGroupOptionGroupDB.getSizeGroupId());

        if (optionDTOS != null) {
            Long sizeGroupId = sizeGroupOptionGroupDB.getSizeGroupId();

            List<SizeOptionDTO> allSizeOptions = sizeOptionRepository.findAllSizeOptions();

            for (OptionDTO optionDTO : optionDTOS) {
                List<SizeDTO> sizeDTOS = sizeRepository.getBySizeGroupId(sizeGroupId);

                if (sizeDTOS != null && !sizeDTOS.isEmpty()) {
                    for (SizeDTO sizeDTO : sizeDTOS) {
                        Long sizeId = sizeDTO.getId();
                        Long optionId = optionDTO.getId();

                        for (SizeOptionDTO sizeOptionDTO : allSizeOptions) {
                            if (sizeOptionDTO.getSizeId().equals(sizeId) && sizeOptionDTO.getOptionId().equals(optionId)) {
                                sizeDTO.setPrice(sizeOptionDTO.getPrice());
                            }
                        }
                    }
                }

                optionDTO.setSizes(sizeDTOS);
            }
        }

        SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO = new SizeGroupOptionGroupDTO();

        sizeGroupOptionGroupDTO.setOptions(optionDTOS);
        return sizeGroupOptionGroupDTO;
    }


    @Override
    public SizeGroupOptionGroupDTO handleExtraPrices(SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO) {

        SizeGroupOptionGroupDB sizeGroupOptionGroupDB = sizeGroupOptionGroupRepository.findById(sizeGroupOptionGroupDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("SizeGroupOptionGroup with id " + sizeGroupOptionGroupDTO.getSizeGroupId() + " not found"));

        List<Long> optionIds = sizeGroupOptionGroupDTO.getOptions().stream()
                .map(OptionDTO::getId)
                .collect(Collectors.toList());

        List<OptionDB> optionDBS = optionRepository.findAll();

        List<OptionDB> filteredOptionDTOs = optionDBS.stream()
                .filter(optionDB -> optionIds.contains(optionDB.getId()))
                .collect(Collectors.toList());

        Map<Long, OptionDB> optionDBMap = filteredOptionDTOs.stream()
                .collect(Collectors.toMap(OptionDB::getId, optionDB -> optionDB));

        List<SizeDB> sizeDBS = sizeRepository.findBySizeGroupId(sizeGroupOptionGroupDB.getSizeGroupId());

        Map<Long, SizeDB> sizeDBMap = sizeDBS.stream()
                .collect(Collectors.toMap(SizeDB::getId, sizeDB -> sizeDB));

        for (Map.Entry<Long, OptionDB> optionDBEntry : optionDBMap.entrySet()) {
            Long optionId = optionDBEntry.getKey();
            OptionDB optionDB = optionDBEntry.getValue();

            OptionDTO optionDTO = sizeGroupOptionGroupDTO.getOptions().stream()
                    .filter(opt -> opt.getId().equals(optionId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("OptionDTO with id " + optionId + " not found"));

            for (Map.Entry<Long, SizeDB> sizeDBEntry : sizeDBMap.entrySet()) {
                Long sizeId = sizeDBEntry.getKey();
                SizeDB sizeDB = sizeDBEntry.getValue();

                SizeDTO sizeDTO = optionDTO.getSizes().stream()
                        .filter(size -> size.getId().equals(sizeId))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("SizeDTO with id " + sizeId + " not found"));

                SizeOptionDB sizeOptionDB = new SizeOptionDB();
                sizeOptionDB.setId(optionId);
                sizeOptionDB.setOptionId(optionId);
                sizeOptionDB.setSizeId(sizeId);
                sizeOptionDB.setOption(optionDB);
                sizeOptionDB.setSize(sizeDB);
                sizeOptionDB.setSizeGroupOptionGroupId(sizeGroupOptionGroupDB.getSizeGroupId());
                sizeOptionDB.setSizeGroupOptionGroup(sizeGroupOptionGroupDB);
                sizeOptionDB.setPrice(sizeDTO.getPrice());

                sizeOptionRepository.save(sizeOptionDB);
            }
        }

        return sizeGroupOptionGroupDTO;
    }
}