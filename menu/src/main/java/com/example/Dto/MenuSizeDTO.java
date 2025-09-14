package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuSizeDTO {

    private Long id;

    private float price;

    private Long menuId;

    private Long sizeId;
}
