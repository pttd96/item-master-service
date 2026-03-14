package com.example.itemmasterservice.controller;

import com.example.itemmasterservice.model.Buyer;
import com.example.itemmasterservice.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyers")
public class BuyerController {
    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping
    public List<Buyer> all() {
        return buyerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buyer> getById(@PathVariable Long id) {
        Buyer buyer = buyerService.findById(id);
        return buyer != null ? ResponseEntity.ok(buyer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Buyer create(@RequestBody Buyer buyer) {
        return buyerService.save(buyer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> update(@PathVariable Long id, @RequestBody Buyer payload) {
        Buyer stored = buyerService.findById(id);
        if (stored == null) {
            return ResponseEntity.notFound().build();
        }
        stored.setName(payload.getName());
        stored.setEmail(payload.getEmail());
        return ResponseEntity.ok(buyerService.save(stored));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Buyer stored = buyerService.findById(id);
        if (stored == null) {
            return ResponseEntity.notFound().build();
        }
        buyerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
