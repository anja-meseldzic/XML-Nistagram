package app.agent.service;

import app.agent.model.Purchase;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PurchaseService {
    void create(String username, Long productId);

    Collection<Purchase> getAllAfterDate(LocalDateTime dateTime);
}
