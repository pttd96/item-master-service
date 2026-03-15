package com.example.itemmasterservice.service;

import com.example.itemmasterservice.model.Item;
import com.example.itemmasterservice.model.Validation;
import com.example.itemmasterservice.repository.ValidationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationService {
    private final ValidationRepository validationRepository;

    public ValidationService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    public Validation save(Validation validation) {
        return validationRepository.save(validation);
    }

    public List<Validation> findAll() {
        return validationRepository.findAll();
    }

    public Validation findById(Long id) {
        return validationRepository.findById(id).orElse(null);
    }

    public boolean isPriceChangeWithinThreshold(Item item, double newPrice) {
        List<Validation> validations = validationRepository.findByItemClassAndSubclassAndDepartmentAndIsActiveTrue(
                item.getItemClass(), item.getSubclass(), item.getDepartment());
        if (validations.isEmpty()) {
            return false; // No active validation means no restrictions, so within
        }
        double currentPrice = item.getPrice();
        double percentage = Math.abs((newPrice - currentPrice) / currentPrice) * 100;
        for (Validation v : validations) {
            if (percentage >= v.getMinThreshold() && percentage <= v.getMaxThreshold()) {
                return false; // within for this validation
            }
        }
        return true; // not within for any
    }
}
