package com.example.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeDTO {



    public SizeDTO(Long id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
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
