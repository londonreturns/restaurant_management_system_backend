package com.example.Service.Impl;

import com.example.Dto.SizeGroupDTO;
import com.example.Exception.ResourceNotFoundException;
import com.example.Model.SizeGroupDB;
import com.example.Repository.SizeGroupRepository;
import com.example.Service.SizeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeGroupServiceImpl implements SizeGroupService {

    @Autowired
    private SizeGroupRepository sizeGroupRepository;

    @Override
    public SizeGroupDTO createSizeGroup(SizeGroupDTO sizeGroupDTO) {
        return convertToDTO(sizeGroupRepository.save(convertToEntity(sizeGroupDTO)));
    }

    @Override
    public SizeGroupDTO getSizeGroupById(Long id) {
        return convertToDTO(sizeGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size Group " + id + " Not Found")));
    }

    @Override
    public List<SizeGroupDTO> getAllSizeGroups() {
        List<SizeGroupDB> sizeGroupDBs = sizeGroupRepository.findAll();
        return sizeGroupDBs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public SizeGroupDTO updateSizeGroup(SizeGroupDTO sizeGroupDTO) {
        Long id = sizeGroupDTO.getId();
        SizeGroupDB oldSizeGroup = sizeGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size Group " + id + " Not Found"));

        SizeGroupDB updatedSizeGroup = updateSizeGroupDetails(oldSizeGroup, convertToEntity(sizeGroupDTO));
        return convertToDTO(sizeGroupRepository.save(updatedSizeGroup));
    }

    @Override
    public String deleteSizeGroup(Long id) {
        sizeGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size Group " + id + " Not Found"));

        sizeGroupRepository.deleteById(id);
        return "Size Group " + id + " Deleted";
    }

    private SizeGroupDB updateSizeGroupDetails(SizeGroupDB oldSizeGroup, SizeGroupDB newSizeGroup) {
        newSizeGroup.setId(oldSizeGroup.getId());
        newSizeGroup.setName(oldSizeGroup.getName());
        return newSizeGroup;
    }

    private SizeGroupDB convertToEntity(SizeGroupDTO sizeGroupDTO) {
        SizeGroupDB sizeGroupDB = new SizeGroupDB();
        sizeGroupDB.setId(sizeGroupDTO.getId());
        sizeGroupDB.setName(sizeGroupDTO.getName());
        return sizeGroupDB;
    }

    private SizeGroupDTO convertToDTO(SizeGroupDB sizeGroup) {
        SizeGroupDTO sizeGroupDTO = new SizeGroupDTO();
        sizeGroupDTO.setId(sizeGroup.getId());
        sizeGroupDTO.setName(sizeGroup.getName());
        return sizeGroupDTO;
    }
}
