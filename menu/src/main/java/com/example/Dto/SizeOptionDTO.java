package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeOptionDTO {

    private Long id;

    private float price;

    private Long sizeId;

    private Long optionId;
}
