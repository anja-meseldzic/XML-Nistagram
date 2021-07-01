package app.agent.service;

import app.agent.model.Purchase;

public interface PurchaseService {
    void create(String username, Long productId);
}
