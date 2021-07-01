package app.agent.service.impl;

import app.agent.model.Product;
import app.agent.model.dtos.ProductDTO;
import app.agent.repository.ProductRepository;
import app.agent.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Value("${media.storage}")
    private String storageDirectory;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Collection<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void update(Product product) {
        productRepository.save(product);
    }

    @Override
    public void create(MultipartFile file, ProductDTO productDTO) throws IOException {
        String filename = saveFile(file, storageDirectory);
        String fileDownloadUri = "merch/content/" + filename;
        System.out.println(fileDownloadUri);

        Product product = productDTO.getProduct();
        product.setImagePath(fileDownloadUri);
        productRepository.save(product);
    }

    @Override
    public UrlResource getContent(String contentName) throws MalformedURLException {
        return new UrlResource("file:" + storageDirectory + File.separator + contentName);
    }

    private String saveFile(MultipartFile file, String storagePath) throws IOException {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = getFileExtension(originalFileName);
        String filename = UUID.randomUUID().toString() + "." + extension;

        Path storage = Paths.get(storagePath);
        if(!Files.exists(storage))
            Files.createDirectories(storage);

        Path dest = Paths.get(storagePath.toString() + File.separator + filename);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    private String getFileExtension(String filename) throws IOException {
        String[] parts = filename.split("\\.");
        if(parts.length > 0)
            return parts[parts.length - 1];
        else
            throw new IOException();
    }
}
