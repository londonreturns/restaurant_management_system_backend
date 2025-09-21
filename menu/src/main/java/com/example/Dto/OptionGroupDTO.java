package com.example.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionGroupDTO {

    public OptionGroupDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;

    private String name;

    private List<OptionDTO> options;

    private boolean isSelected;
}
