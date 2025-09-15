package com.example.Service;

import com.example.Dto.SizeDTO;

import java.util.List;

public interface SizeService {

    SizeDTO createSize(SizeDTO dto);

    SizeDTO findSizeById(Long id);

    List<SizeDTO> findAllSize();

    SizeDTO updateSize(SizeDTO dto);

    String deleteSize(Long id);
}
