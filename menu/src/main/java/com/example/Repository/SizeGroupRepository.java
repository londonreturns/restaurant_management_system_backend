package com.example.Repository;

import com.example.Model.SizeGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeGroupRepository extends JpaRepository<SizeGroupDB, Long> {
}
