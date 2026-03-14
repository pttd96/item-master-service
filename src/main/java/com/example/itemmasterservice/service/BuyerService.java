package com.example.itemmasterservice.service;

import com.example.itemmasterservice.model.Buyer;
import com.example.itemmasterservice.repository.BuyerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerService {
    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public List<Buyer> findAll() {
        return buyerRepository.findAll();
    }

    public Buyer findById(Long id) {
        return buyerRepository.findById(id).orElse(null);
    }

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public void deleteById(Long id) {
        buyerRepository.deleteById(id);
    }
}
