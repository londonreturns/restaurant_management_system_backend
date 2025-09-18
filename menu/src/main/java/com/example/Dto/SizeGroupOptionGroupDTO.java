package com.example.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeGroupOptionGroupDTO {

    private Long id;

    private Long optionGroupId;

    private Long sizeGroupId;

    private List<OptionDTO> options;
}
