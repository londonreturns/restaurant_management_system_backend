package com.example.Repository;

import com.example.Model.OptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroupDB, Long> {
}
