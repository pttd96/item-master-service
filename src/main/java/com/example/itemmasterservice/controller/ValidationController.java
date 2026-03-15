package com.example.itemmasterservice.controller;

import com.example.itemmasterservice.model.Validation;
import com.example.itemmasterservice.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/validations")
public class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping
    public List<Validation> getAll() {
        return validationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Validation> getById(@PathVariable Long id) {
        Validation validation = validationService.findById(id);
        return validation != null ? ResponseEntity.ok(validation) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Validation> create(@RequestBody Validation validation) {
        Validation saved = validationService.save(validation);
        return ResponseEntity.ok(saved);
    }
}
