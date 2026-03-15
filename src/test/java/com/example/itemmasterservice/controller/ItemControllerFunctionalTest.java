package com.example.itemmasterservice.controller;

import com.example.itemmasterservice.dto.ItemUpdate;
import com.example.itemmasterservice.dto.ItemUpdateResult;
import com.example.itemmasterservice.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerFunctionalTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void updateItem_shouldReturnSuccess_whenUpdatingNonPriceFields() {
        // Given
        ItemUpdate update = new ItemUpdate();
        update.setItemClass("Updated Electronics");
        update.setSubclass("Updated Phone");

        // When & Then
        webTestClient.put()
                .uri("/items/1")
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ItemUpdateResult.class)
                .value(result -> {
                    assertThat(result.getStatus()).isEqualTo(ItemUpdateResult.Status.SUCCESS);
                    assertThat(result.getMessage()).isEqualTo("Update successful");
                    assertThat(result.getBuyerEmail()).isNull();
                });
    }

    @Test
    void updateItem_shouldReturnSuccess_whenPriceChangeWithinThreshold() {
        // Given - Item 1 has price 699.99, validation allows 5-25% change
        // 699.99 * 1.15 = ~804.99 (15% increase, within 25% max threshold)
        ItemUpdate update = new ItemUpdate();
        update.setPrice(804.99);

        // When & Then
        webTestClient.put()
                .uri("/items/1")
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ItemUpdateResult.class)
                .value(result -> {
                    assertThat(result.getStatus()).isEqualTo(ItemUpdateResult.Status.SUCCESS);
                    assertThat(result.getMessage()).isEqualTo("Update successful");
                    assertThat(result.getBuyerEmail()).isNull();
                });
    }

    @Test
    void updateItem_shouldReturnPendingApproval_whenPriceChangeExceedsThreshold() {
        // Given - Item 1 has price 699.99, validation allows max 25% change
        // 699.99 * 1.30 = ~909.99 (30% increase, exceeds 25% max threshold)
        ItemUpdate update = new ItemUpdate();
        update.setPrice(909.99);

        // When & Then
        webTestClient.put()
                .uri("/items/1")
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ItemUpdateResult.class)
                .value(result -> {
                    assertThat(result.getStatus()).isEqualTo(ItemUpdateResult.Status.PENDING_APPROVAL);
                    assertThat(result.getMessage()).isEqualTo("Price change requires approval");
                    assertThat(result.getBuyerEmail()).isEqualTo("alice@example.com");
                });
    }

    @Test
    void updateItem_shouldReturnNotFound_whenItemDoesNotExist() {
        // Given
        ItemUpdate update = new ItemUpdate();
        update.setPrice(100.0);

        // When & Then
        webTestClient.put()
                .uri("/items/999")
                .bodyValue(update)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateItem_shouldReturnPendingApprovalWithNullEmail_whenBuyerNotFound() {
        // Given - Create a new item with non-existent buyer ID
        // First create an item
        Item newItem = new Item("Test", "Test", "Test", 100.0, 999L);

        webTestClient.post()
                .uri("/items")
                .bodyValue(newItem)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Item.class)
                .value(item -> {
                    // Now try to update price with a change that requires approval
                    ItemUpdate update = new ItemUpdate();
                    update.setPrice(200.0); // 100% increase, should exceed any threshold

                    webTestClient.put()
                            .uri("/items/" + item.getId())
                            .bodyValue(update)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(ItemUpdateResult.class)
                            .value(result -> {
                                assertThat(result.getStatus()).isEqualTo(ItemUpdateResult.Status.PENDING_APPROVAL);
                                assertThat(result.getMessage()).isEqualTo("Price change requires approval");
                                assertThat(result.getBuyerEmail()).isNull();
                            });
                });
    }
}