package com.example.Repository;

import com.example.Model.OptionDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionDB, Long> {
}
