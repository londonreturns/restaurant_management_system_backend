package com.example.Service;

import com.example.Dto.SizeGroupDTO;

import java.util.List;

public interface SizeGroupService {
    SizeGroupDTO createSizeGroup(SizeGroupDTO sizeGroupDTO);

    SizeGroupDTO getSizeGroupById(Long id);

    List<SizeGroupDTO> getAllSizeGroups();

    SizeGroupDTO updateSizeGroup(SizeGroupDTO sizeGroupDTO);

    String deleteSizeGroup(Long id);

}
