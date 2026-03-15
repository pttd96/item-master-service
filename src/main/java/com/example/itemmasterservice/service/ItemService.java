package com.example.itemmasterservice.service;

import com.example.itemmasterservice.dto.ItemUpdate;
import com.example.itemmasterservice.dto.ItemUpdateResult;
import com.example.itemmasterservice.model.Buyer;
import com.example.itemmasterservice.model.Item;
import com.example.itemmasterservice.repository.ItemRepository;
import com.example.itemmasterservice.service.BuyerService;
import com.example.itemmasterservice.service.ValidationService;
import static com.example.itemmasterservice.dto.ItemUpdateResult.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ValidationService validationService;
    private final BuyerService buyerService;

    public ItemService(ItemRepository itemRepository, ValidationService validationService, BuyerService buyerService) {
        this.itemRepository = itemRepository;
        this.validationService = validationService;
        this.buyerService = buyerService;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public ItemUpdateResult updateItem(Long id, ItemUpdate update) {
        Item item = findById(id);
        if (item == null) {
            return null;
        }
        if (update.getItemClass() != null) item.setItemClass(update.getItemClass());
        if (update.getSubclass() != null) item.setSubclass(update.getSubclass());
        if (update.getDepartment() != null) item.setDepartment(update.getDepartment());
        if (update.getPrice() != null) item.setPrice(update.getPrice());
        if (update.getBuyerId() != null) item.setBuyerId(update.getBuyerId());
    
        // update price as the last step to ensure validation is based on the most up-to-date item info
        // saving to repository is handled in the updatePrice method to ensure price validation is applied
        return updatePrice(item, update);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    private ItemUpdateResult updatePrice(Item item, ItemUpdate update) {
        if (update.getPrice() == null) {
            itemRepository.save(item);
            return new ItemUpdateResult(Status.SUCCESS, "Update successful", null);
        }
        double newPrice = update.getPrice();
        boolean requiresApproval = requiresApproval(item, newPrice);
        if (requiresApproval) {
            itemRepository.save(item);
            Buyer buyer = buyerService.findById(item.getBuyerId());
            // if approval needed, return buyer email for front-end to send notification
            return new ItemUpdateResult(Status.PENDING_APPROVAL, "Price change requires approval", buyer != null ? buyer.getEmail() : null);
        }
        item.setPrice(newPrice);
        itemRepository.save(item);
        // if no approval needed, no need to return buyer email
        return new ItemUpdateResult(Status.SUCCESS, "Update successful", null);
    }

    private boolean requiresApproval(Item item, double newPrice) {
        return validationService.isPriceChangeWithinThreshold(item, newPrice);
    }

}
