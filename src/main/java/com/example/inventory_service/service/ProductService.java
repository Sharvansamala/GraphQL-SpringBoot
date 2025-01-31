package com.example.inventory_service.service;

import com.example.inventory_service.entity.Product;
import com.example.inventory_service.exception.ProductNotFound;
import com.example.inventory_service.payload.ProductDTO;
import com.example.inventory_service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductDTO addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    //sales team update quantity
    public ProductDTO updateStock(Long id, Integer stock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Not found"));

        product.setStock(stock);
        Product product1 = productRepository.save(product);
        return modelMapper.map(product1, ProductDTO.class);
    }

    //warehouse: receive new shipment
    public ProductDTO receiveNewShipment(Long id, Integer stock) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found in db"));
        existingProduct.setStock(existingProduct.getStock() + stock);
        return modelMapper.map(productRepository.save(existingProduct), ProductDTO.class);
    }
}
