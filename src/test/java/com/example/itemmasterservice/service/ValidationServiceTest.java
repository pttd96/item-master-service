package com.example.itemmasterservice.service;

import com.example.itemmasterservice.model.Item;
import com.example.itemmasterservice.model.Validation;
import com.example.itemmasterservice.repository.ValidationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    private ValidationRepository validationRepository;

    @InjectMocks
    private ValidationService validationService;

    private Validation validation;
    private Item item;

    @BeforeEach
    void setUp() {
        validation = new Validation();
        validation.setId(1L);
        validation.setItemClass("Electronics");
        validation.setSubclass("Phones");
        validation.setDepartment("Mobile");
        validation.setMaxThreshold(50.0);
        validation.setMinThreshold(10.0);
        validation.setActive(true);

        item = new Item();
        item.setId(1L);
        item.setItemClass("Electronics");
        item.setSubclass("Phones");
        item.setDepartment("Mobile");
        item.setPrice(100.0);
    }

    @Test
    void save_shouldReturnSavedValidation() {
        // Given
        when(validationRepository.save(any(Validation.class))).thenReturn(validation);

        // When
        Validation result = validationService.save(validation);

        // Then
        assertEquals(validation, result);
        verify(validationRepository).save(validation);
    }

    @Test
    void findAll_shouldReturnAllValidations() {
        // Given
        List<Validation> validations = Arrays.asList(validation);
        when(validationRepository.findAll()).thenReturn(validations);

        // When
        List<Validation> result = validationService.findAll();

        // Then
        assertEquals(validations, result);
        verify(validationRepository).findAll();
    }

    @Test
    void findById_shouldReturnValidation_whenExists() {
        // Given
        when(validationRepository.findById(1L)).thenReturn(java.util.Optional.of(validation));

        // When
        Validation result = validationService.findById(1L);

        // Then
        assertEquals(validation, result);
        verify(validationRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        // Given
        when(validationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // When
        Validation result = validationService.findById(1L);

        // Then
        assertNull(result);
        verify(validationRepository).findById(1L);
    }

    @Test
    void isPriceChangeWithinThreshold_shouldReturnFalse_whenNoActiveValidations() {
        // Given
        when(validationRepository.findByItemClassAndSubclassAndDepartmentAndIsActiveTrue(
                anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());

        // When
        boolean result = validationService.isPriceChangeWithinThreshold(item, 150.0);

        // Then
        assertFalse(result);
    }

    @Test
    void isPriceChangeWithinThreshold_shouldReturnFalse_whenPriceChangeWithinThreshold() {
        // Given
        List<Validation> validations = Arrays.asList(validation);
        when(validationRepository.findByItemClassAndSubclassAndDepartmentAndIsActiveTrue(
                "Electronics", "Phones", "Mobile")).thenReturn(validations);

        // Price change: from 100 to 120 = 20% increase, which is between 10% and 50%
        // When
        boolean result = validationService.isPriceChangeWithinThreshold(item, 120.0);

        // Then
        assertFalse(result); // within threshold means no approval needed
    }

    @Test
    void isPriceChangeWithinThreshold_shouldReturnTrue_whenPriceChangeOutsideThreshold() {
        // Given
        List<Validation> validations = Arrays.asList(validation);
        when(validationRepository.findByItemClassAndSubclassAndDepartmentAndIsActiveTrue(
                "Electronics", "Phones", "Mobile")).thenReturn(validations);

        // Price change: from 100 to 160 = 60% increase, which is above 50%
        // When
        boolean result = validationService.isPriceChangeWithinThreshold(item, 160.0);

        // Then
        assertTrue(result); // outside threshold means approval needed
    }

    @Test
    void isPriceChangeWithinThreshold_shouldReturnTrue_whenPriceChangeBelowMinThreshold() {
        // Given
        List<Validation> validations = Arrays.asList(validation);
        when(validationRepository.findByItemClassAndSubclassAndDepartmentAndIsActiveTrue(
                "Electronics", "Phones", "Mobile")).thenReturn(validations);

        // Price change: from 100 to 105 = 5% increase, which is below 10%
        // When
        boolean result = validationService.isPriceChangeWithinThreshold(item, 105.0);

        // Then
        assertTrue(result); // below min threshold means approval needed
    }
}