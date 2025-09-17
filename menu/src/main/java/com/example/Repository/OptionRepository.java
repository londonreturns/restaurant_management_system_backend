package com.example.Repository;

import com.example.Model.OptionDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<OptionDB, Long> {
    List<OptionDB> findByOptionGroupId(Long optionGroupId);
}
