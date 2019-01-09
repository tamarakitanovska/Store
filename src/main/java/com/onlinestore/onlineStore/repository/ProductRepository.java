package com.onlinestore.onlineStore.repository;

import com.onlinestore.onlineStore.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
}
