package com.tomtom.ecommerce.product.repository;

import org.springframework.data.repository.CrudRepository;

import com.tomtom.ecommerce.product.model.Product;

public interface ProductDataAccessRepository  extends CrudRepository<Product, Integer>{ 

}