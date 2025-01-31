package com.example.inventory_service.controller;

import com.example.inventory_service.entity.Product;
import com.example.inventory_service.payload.ProductDTO;
import com.example.inventory_service.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("product")
    public ProductDTO addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @QueryMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public List<ProductDTO> getProductByCategory(@Argument String category) {
        return productService.getProductsByCategory(category);
    }

    @MutationMapping
    public ProductDTO updateStock(@Argument Long id, @Argument Integer stock) {
        return productService.updateStock(id, stock);
    }

    //    @MutationMapping(name = "receiveNewShipment")
    @SchemaMapping(typeName = "Mutation", field = "receiveNewShipment")
    public ProductDTO receiveNewShipment(@Argument Long id, @Argument Integer stock) {
        return productService.receiveNewShipment(id, stock);
    }
}