package com.example.Repository;

import com.example.Dto.SizeDTO;
import com.example.Model.SizeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeDB, Long> {
    List<SizeDB> findBySizeGroupId(Long sizeGroupId);

    @Query("SELECT new com.example.Dto.SizeDTO(s.id, s.name, ms.price) FROM com.example.Model.SizeDB s " +
            " JOIN menu_size ms ON ms.sizeId = s.id" +
            " WHERE s.sizeGroupId = :sizeGroupId")
    List<SizeDTO> getBySizeGroupId(@Param("sizeGroupId") Long sizeGroupId);

    @Query("SELECT new com.example.Dto.SizeDTO(s.id, s.name, s.sizeGroupId) FROM com.example.Model.SizeDB s ")
    List<SizeDTO> findAllSizeDTO();
}
