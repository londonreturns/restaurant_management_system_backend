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

    private Long id;

    private String name;

    private float basePrice;

    private Long categoryId;

    private Long sizeGroupId;

    List<MenuSizeDTO> menuSizes;

    List<OptionDTO> options;

    List<OptionDTO> removedOptions;

    List<SizeDTO> sizes;

    List<OptionDTO> menuOptions;

    List<OptionGroupDTO> optionGroups;
}