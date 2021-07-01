package app.agent.model.dtos;

import app.agent.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private int quantity;

    public Product getProduct() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setActive(true);
        return product;
    }
}
