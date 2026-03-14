package com.example.itemmasterservice.controller;

import com.example.itemmasterservice.model.Item;
import com.example.itemmasterservice.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        Item item = itemService.findById(id);
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return itemService.save(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item payload) {
        Item item = itemService.findById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        item.setItemClass(payload.getItemClass());
        item.setSubclass(payload.getSubclass());
        item.setDepartment(payload.getDepartment());
        item.setPrice(payload.getPrice());
        item.setBuyerId(payload.getBuyerId());
        return ResponseEntity.ok(itemService.save(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (itemService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
