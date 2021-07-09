package app.agent.service.impl;

import app.agent.model.Product;
import app.agent.model.Purchase;
import app.agent.model.User;
import app.agent.repository.ProductRepository;
import app.agent.repository.PurchaseRepository;
import app.agent.repository.UserRepository;
import app.agent.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void create(String username, Long productId) {
        User user = userRepository.findUserByUsername(username);
        if(user == null)
            throw new IllegalArgumentException("No such user");
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent())
            throw new IllegalArgumentException("No such product");
        product.get().decrementQuantity();
        productRepository.save(product.get());

        Purchase purchase = new Purchase();
        purchase.setProduct(product.get());
        purchase.setUser(user);
        purchase.setTimestamp(LocalDateTime.now());
        purchaseRepository.save(purchase);
    }

    @Override
    public Collection<Purchase> getAllAfterDate(LocalDateTime dateTime) {
        return purchaseRepository.findAll().stream()
                .filter(p -> p.getTimestamp().isAfter(dateTime))
                .collect(Collectors.toList());
    }
}
