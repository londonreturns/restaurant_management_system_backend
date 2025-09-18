package com.example.Repository;

import com.example.Dto.OptionDTO;
import com.example.Dto.SizeOptionDTO;
import com.example.Model.SizeOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeOptionRepository extends JpaRepository<SizeOptionDB, Long> {

    @Query("SELECT new com.example.Dto.SizeOptionDTO(s.id, s.price, s.sizeId, s.optionId) FROM com.example.Model.SizeOptionDB s")
    List<SizeOptionDTO> findAllSizeOptions();

    @Query("SELECT DISTINCT new com.example.Dto.OptionDTO(o.id, o.name) " +
            "FROM com.example.Model.SizeOptionDB so " +
            "JOIN com.example.Model.OptionDB o ON o.id = so.optionId " +
            "JOIN com.example.Model.SizeDB s ON s.id = so.sizeId " +
            "WHERE s.sizeGroupId = :sizeGroupId" )
    List<OptionDTO> findBySizeGroupOptionGroupId(@Param("sizeGroupId") Long sizeGroupId);
}
