package com.tomtom.ecommerce.product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tomtom.ecommerce.product.model.Product;
@Repository
public interface ProductDataAccessRepository  extends CrudRepository<Product, Integer>{ 

}