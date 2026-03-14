package com.example.itemmasterservice.service;

import com.example.itemmasterservice.model.Validation;
import com.example.itemmasterservice.repository.ValidationRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    private final ValidationRepository validationRepository;

    public ValidationService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    public Validation save(Validation validation) {
        return validationRepository.save(validation);
    }
}
