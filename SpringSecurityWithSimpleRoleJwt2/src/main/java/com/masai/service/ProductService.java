package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.model.Product;
import com.masai.repository.ProductRepo;

@Service
public class ProductService {
	private ProductRepo productRepo;

	@Autowired
	public ProductService(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}

	public Product addItem(Product product) {
		return productRepo.save(product);
	}

	public List<Product> getItems() {
		return productRepo.findAll();
	}

	public Optional<Product> getItemById(Long id) {
		return productRepo.findById(id);
	}

	public Optional<Product> updateItem(Long id, Product product) {
		Optional<Product> optionalItem = productRepo.findById(id);
		if (optionalItem.isPresent()) {
			Product existingItem = optionalItem.get();
			existingItem.setName(product.getName());
			existingItem.setDescription(product.getDescription());
			Product savedItem = productRepo.save(existingItem);
			return Optional.of(savedItem);
		} else {
			return Optional.empty();
		}
	}

	public boolean deleteItem(Long id) {
		Optional<Product> optionalItem = productRepo.findById(id);
		if (optionalItem.isPresent()) {
			productRepo.delete(optionalItem.get());
			return true;
		} else {
			return false;
		}
	}

	public List<Product> searchItems(String name) {
		return productRepo.findByNameContaining(name);
	}
}
