package com.tomtom.ecommerce.product.service;

import java.util.List;

import com.tomtom.ecommerce.product.exception.NotFoundECommerceException;
import com.tomtom.ecommerce.product.exception.PriceMisMatchECommerceException;
import com.tomtom.ecommerce.product.exception.ProductNotFoundECommerceException;
import com.tomtom.ecommerce.product.model.Product;

public interface EcommerceProductService {

	List<Product> getproducts() throws NotFoundECommerceException;

	void addProduct(Product product) throws PriceMisMatchECommerceException;

	Product getProduct(Integer productId) throws ProductNotFoundECommerceException;
}
