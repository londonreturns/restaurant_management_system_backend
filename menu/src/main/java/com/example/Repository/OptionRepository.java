package com.example.Repository;

import com.example.Dto.OptionDTO;
import com.example.Model.OptionDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OptionRepository extends JpaRepository<OptionDB, Long> {
    List<OptionDB> findByOptionGroupId(Long optionGroupId);

    @Query("SELECT new com.example.Dto.OptionDTO(o.id, o.name, o.optionGroupId) " +
            "FROM com.example.Model.OptionDB o " +
            "WHERE o.optionGroupId IN :optionIds")
    List<OptionDTO> findDTOSByIds(@Param("optionIds") Set<Long> optionIds);
}

