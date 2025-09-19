package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuSizeDTO {

    public MenuSizeDTO(Long id, float price, Long menuId) {
        this.id = id;
        this.price = price;
        this.menuId = menuId;
    }

    private Long id;

    private float price;

    private Long menuId;

    private Long sizeId;
}
