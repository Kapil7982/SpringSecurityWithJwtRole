package com.masai.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.model.Product;
import com.masai.repository.ProductRepo;

@RestController
public class ProductController {
	
    private ProductRepo productRepo;

    @Autowired
    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @PostMapping("/items")
    public ResponseEntity<Product> addItem(@RequestBody Product product) {
    	Product savedItem = productRepo.save(product);
        return ResponseEntity.ok(savedItem);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Product>> getItems() {
        List<Product> items = productRepo.findAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Product> getItemById(@PathVariable Long id) {
        Optional<Product> optionalItem = productRepo.findById(id);
        return optionalItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Product> updateItem(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> optionalItem = productRepo.findById(id);
        if (optionalItem.isPresent()) {
        	Product existingItem = optionalItem.get();
            existingItem.setName(product.getName());
            existingItem.setDescription(product.getDescription());
            Product savedItem = productRepo.save(existingItem);
            return ResponseEntity.ok(savedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Optional<Product> optionalItem = productRepo.findById(id);
        if (optionalItem.isPresent()) {
        	productRepo.delete(optionalItem.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/items/search")
    public ResponseEntity<List<Product>> searchItems(@RequestParam("name") String name) {
        List<Product> matchingItems = productRepo.findByNameContaining(name);
        return ResponseEntity.ok(matchingItems);
    }
}
