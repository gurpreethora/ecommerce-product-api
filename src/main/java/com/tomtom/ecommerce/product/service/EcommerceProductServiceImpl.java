package com.tomtom.ecommerce.product.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomtom.ecommerce.product.exception.NotFoundECommerceException;
import com.tomtom.ecommerce.product.exception.PriceMisMatchECommerceException;
import com.tomtom.ecommerce.product.exception.ProductNotFoundECommerceException;
import com.tomtom.ecommerce.product.model.Product;
import com.tomtom.ecommerce.product.repository.ProductDataAccessRepository;

/**
 * @author Gurpreet Hora
 *
 */
@Service
@Transactional
public class EcommerceProductServiceImpl implements EcommerceProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EcommerceProductServiceImpl.class);

	private final ProductDataAccessRepository productDataAccessRepository;
	
	@Autowired
	public EcommerceProductServiceImpl(ProductDataAccessRepository productDataAccessRepository) {
		super();
		this.productDataAccessRepository = productDataAccessRepository;
	}

	@Override
	public List<Product> getproducts() throws NotFoundECommerceException {
		return (List<Product>) productDataAccessRepository.findAll();
	}

	@Override
	public void addProduct(Product product) throws PriceMisMatchECommerceException {
		LOGGER.debug("Trying to add product {}" ,product.getProductName());
		Optional<Product> existingProduct = this.productDataAccessRepository.findById(product.getProductId());
		if (existingProduct.isPresent()) {
			if(product.getProductPrice()!=null && existingProduct.get().getProductPrice().compareTo(product.getProductPrice()) !=0) {
				throw new PriceMisMatchECommerceException("Existing price for same product is "+existingProduct.get().getProductPrice()+" "
						+ "and new price supplied is "+product.getProductPrice()+", both should be same !");
			}
			existingProduct.get().setProductQuantity(existingProduct.get().getProductQuantity() + product.getProductQuantity());
			productDataAccessRepository.save(existingProduct.get());
		} else {
			productDataAccessRepository.save(product);
		}
		
		LOGGER.debug("Transaction successful to add product {}" ,product.getProductName());
	}

	@Override
	public Product getProduct(Integer productId) throws ProductNotFoundECommerceException  {
		LOGGER.debug("Trying to get product if{}" ,productId);
		Optional<Product> existingProduct = this.productDataAccessRepository.findById(productId);
		if (existingProduct.isPresent()) {
			LOGGER.debug("Product found for product id {}" ,productId);
			return existingProduct.get();
		} else {
			throw new ProductNotFoundECommerceException("Product not found for supplied productId : "+productId);
		}
		

	}
	
}
