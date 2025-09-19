package com.example.Repository;

import com.example.Dto.OptionGroupDTO;
import com.example.Model.SizeGroupOptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeGroupOptionGroupRepository extends JpaRepository<SizeGroupOptionGroupDB, Long> {
    List<SizeGroupOptionGroupDB> findAllBySizeGroupId(Long sizeGroupId);

    @Query("SELECT new com.example.Dto.OptionGroupDTO(sgog.optionGroupId, og.name)" +
            " FROM size_group_option_group sgog" +
            " JOIN option_group og" +
            " ON sgog.optionGroupId = og.id" +
            " WHERE sgog.sizeGroupId = :sizeGroupId")
    List<OptionGroupDTO> findAllOptionGroupBySizeGroupId(@Param("sizeGroupId") Long sizeGroupId);
}
