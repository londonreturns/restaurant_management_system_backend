package com.example.Repository;

import com.example.Model.SizeGroupOptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeGroupOptionGroupRepository extends JpaRepository<SizeGroupOptionGroupDB, Long> {
    List<SizeGroupOptionGroupDB> findAllBySizeGroupId(Long sizeGroupId);

}
