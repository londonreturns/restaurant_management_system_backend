package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SizeGroupOptionGroupDTO {

    private Long id;

    private Long optionGroupId;

    private Long sizeGroupId;
}
