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

                        SizeDTO enrichedSize = new SizeDTO(sizeId, sizeDTO.getName());

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

}