package com.example.Service.Impl;

import com.example.Dto.MenuDTO;
import com.example.Dto.OptionDTO;
import com.example.Exception.ResourceNotFoundException;
import com.example.Model.MenuDB;
import com.example.Model.MenuOptionDB;
import com.example.Repository.MenuOptionRepository;
import com.example.Repository.MenuRepository;
import com.example.Repository.OptionRepository;
import com.example.Service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private OptionRepository optionRepository;

    @Transactional
    @Override
    public MenuDTO handleMenuOptionsDetailed(MenuDTO menuDTO) {
        Long menuId = menuDTO.getId();

        MenuDB menuDB = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu with id " + menuId + " not found"));

        List<OptionDTO> userSubmittedOptionDTOs = menuDTO.getOptions();

        Map<Long, MenuOptionDB> savedOptionMap = menuOptionRepository.findAllByMenuId(menuId)
                .stream()
                .collect(Collectors.toMap(MenuOptionDB::getOptionId, menuOption -> menuOption));

        for (OptionDTO userSubmittedOptionDTO : userSubmittedOptionDTOs) {

            MenuOptionDB savedOption = savedOptionMap.get(userSubmittedOptionDTO.getId());

            if (savedOption != null) {
                if (!userSubmittedOptionDTO.isSelected()) {
                    menuOptionRepository.delete(savedOption);
                }
            } else {
                if (userSubmittedOptionDTO.isSelected()) {
                    MenuOptionDB newOption = new MenuOptionDB();
                    newOption.setOptionId(userSubmittedOptionDTO.getId());
                    newOption.setMenuId(menuId);
                    newOption.setMenu(menuDB);
                    newOption.setOption(optionRepository.findById(userSubmittedOptionDTO.getId()).orElseThrow(
                            () -> new ResourceNotFoundException("Option with id " + userSubmittedOptionDTO.getId() + " not found")
                    ));
                    menuOptionRepository.save(newOption);
                }
            }
        }
        return menuDTO;
    }
}
