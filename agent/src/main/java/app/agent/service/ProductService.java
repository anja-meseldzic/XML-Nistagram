package app.agent.service;

import app.agent.model.Product;

import java.util.Collection;

public interface ProductService {
    Collection<Product> getAll();
}
