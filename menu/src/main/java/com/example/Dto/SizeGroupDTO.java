package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeGroupDTO {

    public SizeGroupDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;

    private String name;

    private List<SizeDTO> sizes;
}
