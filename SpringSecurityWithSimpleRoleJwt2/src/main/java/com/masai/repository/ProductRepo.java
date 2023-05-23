package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

	List<Product> findByNameContaining(String name);
}
