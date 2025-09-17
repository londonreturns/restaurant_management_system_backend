package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionDTO {

    private Long id;

    private String name;

    private Long optionGroupId;

    private boolean isSelected;
}
