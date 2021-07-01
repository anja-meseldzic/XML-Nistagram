package app.agent.service;

import app.agent.model.Product;
import app.agent.model.dtos.ProductDTO;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

public interface ProductService {
    Collection<Product> getAll();

    void create(MultipartFile file, ProductDTO productDTO) throws IOException;

    UrlResource getContent(String contentName) throws MalformedURLException;
}
