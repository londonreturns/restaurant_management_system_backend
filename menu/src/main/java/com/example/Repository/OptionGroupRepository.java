package com.example.Repository;

import com.example.Dto.OptionGroupDTO;
import com.example.Model.OptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroupDB, Long> {

    @Query("SELECT new com.example.Dto.OptionGroupDTO(og.id, og.name) FROM com.example.Model.OptionGroupDB og")
    List<OptionGroupDTO> findAllDTOsWithOptions();
}