package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeDTO {

    public SizeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SizeDTO(Long id, String name, Long sizeGroupId) {
        this.id = id;
        this.name = name;
        this.sizeGroupId = sizeGroupId;
    }

    private Long id;

    private String name;

    private float price;

    private Long sizeGroupId;


}
