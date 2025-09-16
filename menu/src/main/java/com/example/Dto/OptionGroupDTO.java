package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionGroupDTO {

    private Long id;

    private String name;

    private List<OptionDTO> options;
}
