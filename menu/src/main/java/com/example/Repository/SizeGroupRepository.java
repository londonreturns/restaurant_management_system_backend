package com.example.Repository;

import com.example.Dto.SizeGroupDTO;
import com.example.Model.SizeGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeGroupRepository extends JpaRepository<SizeGroupDB, Long> {

    @Query("SELECT new com.example.Dto.SizeGroupDTO(sg.id, sg.name) FROM com.example.Model.SizeGroupDB sg")
    List<SizeGroupDTO> findAllSizeGroupDTO();

    @Query("SELECT new com.example.Dto.SizeGroupDTO(sg.id, sg.name) FROM com.example.Model.SizeGroupDB sg" +
            " WHERE sg.id = :sizeGroupId")
    SizeGroupDTO findSizeGroupDTOById(@Param("sizeGroupId") Long sizeGroupId);

    @Query("SELECT new com.example.Dto.SizeGroupDTO(sg.id, sg.name) FROM com.example.Model.SizeGroupDB sg" +
            " WHERE sg.name = :name")
    List<SizeGroupDTO> findByName(@Param("name") String name);
}
