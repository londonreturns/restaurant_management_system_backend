package com.example.Repository;

import com.example.Model.SizeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeDB, Long> {
    List<SizeDB> findBySizeGroupId(Long sizeGroupId);
}
