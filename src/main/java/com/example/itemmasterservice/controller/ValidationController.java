package com.example.itemmasterservice.controller;

import com.example.itemmasterservice.model.Validation;
import com.example.itemmasterservice.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validations")
public class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping
    public ResponseEntity<Validation> create(@RequestBody Validation validation) {
        Validation saved = validationService.save(validation);
        return ResponseEntity.ok(saved);
    }
}
