package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private Long id;

    private String name;

    private float basePrice;

    private Long categoryId;

    private Long sizeGroupId;

    List<MenuSizeDTO> menuSizes;

    List<OptionDTO> options;
}