package com.example.itemmasterservice.service;

import com.example.itemmasterservice.model.Buyer;
import com.example.itemmasterservice.repository.BuyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyerServiceTest {

    @Mock
    private BuyerRepository buyerRepository;

    @InjectMocks
    private BuyerService buyerService;

    private Buyer buyer;

    @BeforeEach
    void setUp() {
        buyer = new Buyer();
        buyer.setId(1L);
        buyer.setName("John Doe");
        buyer.setEmail("john@example.com");
    }

    @Test
    void findAll_shouldReturnAllBuyers() {
        // Given
        List<Buyer> buyers = Arrays.asList(buyer);
        when(buyerRepository.findAll()).thenReturn(buyers);

        // When
        List<Buyer> result = buyerService.findAll();

        // Then
        assertEquals(buyers, result);
        verify(buyerRepository).findAll();
    }

    @Test
    void findById_shouldReturnBuyer_whenExists() {
        // Given
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));

        // When
        Buyer result = buyerService.findById(1L);

        // Then
        assertEquals(buyer, result);
        verify(buyerRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        // Given
        when(buyerRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Buyer result = buyerService.findById(1L);

        // Then
        assertNull(result);
        verify(buyerRepository).findById(1L);
    }

    @Test
    void save_shouldReturnSavedBuyer() {
        // Given
        when(buyerRepository.save(any(Buyer.class))).thenReturn(buyer);

        // When
        Buyer result = buyerService.save(buyer);

        // Then
        assertEquals(buyer, result);
        verify(buyerRepository).save(buyer);
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        // When
        buyerService.deleteById(1L);

        // Then
        verify(buyerRepository).deleteById(1L);
    }
}