package com.example.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OptionDTO {

    public OptionDTO(Long id, String name, Long optionGroupId) {
        this.id = id;
        this.name = name;
        this.optionGroupId = optionGroupId;
    }

    public OptionDTO(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    private Long id;

    private String name;

    private Long optionGroupId;

    private boolean isSelected;

    private Long optionId;

    private List<SizeDTO> sizes;
}
