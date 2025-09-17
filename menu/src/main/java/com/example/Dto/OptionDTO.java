package com.example.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OptionDTO {

    private Long id;

    private String name;

    private Long optionGroupId;

    private boolean isSelected;

    private Long optionId;

}
