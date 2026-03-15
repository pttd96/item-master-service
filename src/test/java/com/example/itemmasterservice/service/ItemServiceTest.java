package com.example.itemmasterservice.service;

import com.example.itemmasterservice.dto.ItemUpdate;
import com.example.itemmasterservice.dto.ItemUpdateResult;
import com.example.itemmasterservice.model.Buyer;
import com.example.itemmasterservice.model.Item;
import com.example.itemmasterservice.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.itemmasterservice.dto.ItemUpdateResult.Status;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private BuyerService buyerService;

    @InjectMocks
    private ItemService itemService;

    private Item item;
    private ItemUpdate itemUpdate;
    private Buyer buyer;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1L);
        item.setItemClass("Electronics");
        item.setSubclass("Phones");
        item.setDepartment("Mobile");
        item.setPrice(100.0);
        item.setBuyerId(1L);

        itemUpdate = new ItemUpdate();
        itemUpdate.setPrice(120.0);

        buyer = new Buyer();
        buyer.setId(1L);
        buyer.setName("John Doe");
        buyer.setEmail("john@example.com");
    }

    @Test
    void findAll_shouldReturnAllItems() {
        // Given
        List<Item> items = Arrays.asList(item);
        when(itemRepository.findAll()).thenReturn(items);

        // When
        List<Item> result = itemService.findAll();

        // Then
        assertEquals(items, result);
        verify(itemRepository).findAll();
    }

    @Test
    void findById_shouldReturnItem_whenExists() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // When
        Item result = itemService.findById(1L);

        // Then
        assertEquals(item, result);
        verify(itemRepository).findById(1L);
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Item result = itemService.findById(1L);

        // Then
        assertNull(result);
        verify(itemRepository).findById(1L);
    }

    @Test
    void createItem_shouldReturnSavedItem() {
        // Given
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // When
        Item result = itemService.createItem(item);

        // Then
        assertEquals(item, result);
        verify(itemRepository).save(item);
    }

    @Test
    void updateItem_shouldReturnNull_whenItemNotFound() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        ItemUpdateResult result = itemService.updateItem(1L, itemUpdate);

        // Then
        assertNull(result);
        verify(itemRepository).findById(1L);
    }

    @Test
    void updateItem_shouldUpdateNonPriceFieldsAndReturnSuccess_whenNoPriceChange() {
        // Given
        ItemUpdate updateWithoutPrice = new ItemUpdate();
        updateWithoutPrice.setItemClass("New Class");
        updateWithoutPrice.setSubclass("New Subclass");

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // When
        ItemUpdateResult result = itemService.updateItem(1L, updateWithoutPrice);

        // Then
        assertEquals(Status.SUCCESS, result.getStatus());
        assertEquals("Update successful", result.getMessage());
        verify(itemRepository).save(item);
        assertEquals("New Class", item.getItemClass());
        assertEquals("New Subclass", item.getSubclass());
    }

    @Test
    void updateItem_shouldReturnSuccess_whenPriceChangeDoesNotRequireApproval() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(validationService.isPriceChangeWithinThreshold(item, 120.0)).thenReturn(false); // false means within threshold
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // When
        ItemUpdateResult result = itemService.updateItem(1L, itemUpdate);

        // Then
        assertEquals(Status.SUCCESS, result.getStatus());
        assertEquals("Update successful", result.getMessage());
        assertNull(result.getBuyerEmail());
        verify(itemRepository).save(item);
        assertEquals(120.0, item.getPrice());
    }

    @Test
    void updateItem_shouldReturnPendingApproval_whenPriceChangeRequiresApproval() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(validationService.isPriceChangeWithinThreshold(item, 120.0)).thenReturn(true); // true means requires approval
        when(buyerService.findById(1L)).thenReturn(buyer);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // When
        ItemUpdateResult result = itemService.updateItem(1L, itemUpdate);

        // Then
        assertEquals(Status.PENDING_APPROVAL, result.getStatus());
        assertEquals("Price change requires approval", result.getMessage());
        assertEquals("john@example.com", result.getBuyerEmail());
        verify(itemRepository).save(item);
        // Price should not be updated when approval is needed
        assertEquals(100.0, item.getPrice());
    }

    @Test
    void updateItem_shouldReturnPendingApprovalWithNullEmail_whenBuyerNotFound() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(validationService.isPriceChangeWithinThreshold(item, 120.0)).thenReturn(true);
        when(buyerService.findById(1L)).thenReturn(null);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        // When
        ItemUpdateResult result = itemService.updateItem(1L, itemUpdate);

        // Then
        assertEquals(Status.PENDING_APPROVAL, result.getStatus());
        assertEquals("Price change requires approval", result.getMessage());
        assertNull(result.getBuyerEmail());
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        // When
        itemService.deleteById(1L);

        // Then
        verify(itemRepository).deleteById(1L);
    }
}