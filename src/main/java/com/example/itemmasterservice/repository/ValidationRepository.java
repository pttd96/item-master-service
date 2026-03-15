package com.example.itemmasterservice.repository;

import com.example.itemmasterservice.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    List<Validation> findByItemClassAndSubclassAndDepartmentAndIsActiveTrue(String itemClass, String subclass, String department);
}
