package com.example.Service.Impl;

import com.example.Dto.SizeDTO;
import com.example.Model.SizeDB;
import com.example.Model.SizeGroupDB;
import com.example.Repository.SizeGroupRepository;
import com.example.Repository.SizeRepository;
import com.example.Service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeGroupRepository sizeGroupRepository;

    @Override
    public SizeDTO createSize(SizeDTO dto) {
        SizeGroupDB sizeGroup = sizeGroupRepository.findById(dto.getSizeGroupId())
                .orElseThrow(() -> new RuntimeException("SizeGroup not found with id: " + dto.getSizeGroupId()));

        SizeDB sizeDB = convertToEntity(dto);

        sizeDB.setSizeGroup(sizeGroup);

        return convertToDto(sizeRepository.save(sizeDB));
    }


    @Override
    public SizeDTO findSizeById(Long id) {
        return convertToDto(sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size with id " + id + " Not Found")));

    }

    @Override
    public List<SizeDTO> findAllSize() {
        return sizeRepository.findAll()
                .stream().map(
                        this::convertToDto
                ).collect(Collectors.toList());
    }

    @Override
    public SizeDTO updateSize(SizeDTO dto) {
        Long id = dto.getId();

        SizeDB oldSize = sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size with id " + id + " Not Found"));

        SizeDB updatedSize = updateSizeDetails(oldSize, convertToEntity(dto));
        return convertToDto(sizeRepository.save(updatedSize));
    }

    @Override
    public String deleteSize(Long id) {
        sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size with id " + id + " Not Found"));

        sizeRepository.deleteById(id);
        return "Size with id " + id + " Deleted";
    }

    private SizeDB updateSizeDetails(SizeDB oldSize, SizeDB newSize) {
        newSize.setId(oldSize.getId());
        newSize.setName(oldSize.getName());
        return newSize;
    }

    private SizeDB convertToEntity(SizeDTO dto) {
        SizeDB size = new SizeDB();
        size.setId(dto.getId());
        size.setName(dto.getName());
        size.setSizeGroupId(dto.getSizeGroupId());
        return size;
    }

    private SizeDTO convertToDto(SizeDB entity) {
        SizeDTO dto = new SizeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSizeGroupId(entity.getSizeGroup().getId());
        return dto;
    }
}
