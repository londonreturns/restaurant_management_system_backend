package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeDTO {

    private Long id;

    private String name;

    private float price;

    private Long sizeGroupId;

    public SizeDTO(Long id, String name, Long sizeGroupId) {
        this.id = id;
        this.name = name;
        this.sizeGroupId = sizeGroupId;
    }
}
