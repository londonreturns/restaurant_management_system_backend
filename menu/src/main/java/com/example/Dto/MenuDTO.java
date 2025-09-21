package com.example.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO {

    public MenuDTO(Long id, String name, float basePrice, Long categoryId, Long sizeGroupId) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.categoryId = categoryId;
        this.sizeGroupId = sizeGroupId;
    }

    private Long id;

    private String name;

    private float basePrice;

    private Long categoryId;

    private Long sizeGroupId;

    private Long optionGroupId;

    List<MenuSizeDTO> menuSizes;

    List<OptionDTO> options;

    List<OptionDTO> removedOptions;

    List<SizeDTO> sizes;

    List<OptionDTO> menuOptions;

    List<OptionGroupDTO> optionGroups;
}