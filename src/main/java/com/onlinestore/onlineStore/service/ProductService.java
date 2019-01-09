package com.onlinestore.onlineStore.service;

import com.onlinestore.onlineStore.model.Product;
import com.onlinestore.onlineStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> findAll(){
        return productRepository.findAll();
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> get(Long id){
        return productRepository.findById(id);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }

}
