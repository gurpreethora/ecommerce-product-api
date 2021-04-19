package com.tomtom.ecommerce.product.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tomtom.ecommerce.product.exception.NotFoundECommerceException;
import com.tomtom.ecommerce.product.exception.PriceMisMatchECommerceException;
import com.tomtom.ecommerce.product.exception.ProductNotFoundECommerceException;
import com.tomtom.ecommerce.product.mock.ProductMockFactory;
import com.tomtom.ecommerce.product.model.Product;
import com.tomtom.ecommerce.product.repository.ProductDataAccessRepository;
@ExtendWith(MockitoExtension.class)
public class EcommerceProductServiceImplTest {

	@InjectMocks
	private EcommerceProductServiceImpl eCommerceServiceImpl;

	@Mock
	ProductDataAccessRepository productDataAccessRepository;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getproductsTest() throws NotFoundECommerceException {
		when(productDataAccessRepository.findAll()).thenReturn(ProductMockFactory.getProducts());
		List<Product> lstProducts = eCommerceServiceImpl.getproducts();
		assertNotNull(lstProducts);
		assertEquals(2, lstProducts.size());
	}
	
	@Test
	public void addProductTest() throws PriceMisMatchECommerceException {
		Optional<Product> product = Optional.ofNullable(ProductMockFactory.getDummyValuedProduct());
		when(productDataAccessRepository.findById(product.get().getProductId())).thenReturn(product);
		when(productDataAccessRepository.save(Mockito.any(Product.class))).thenReturn(new Product());
		eCommerceServiceImpl.addProduct(product.get());
		verify(productDataAccessRepository).save(Mockito.any(Product.class));
	}
	
	@Test(expected = PriceMisMatchECommerceException.class)
	public void addProduct_PriceMisMatchECommerceExceptionTest() throws PriceMisMatchECommerceException {
		Optional<Product> product = Optional.ofNullable(ProductMockFactory.getDummyValuedProduct());
		Product productWithPriceDiff = ProductMockFactory.getDummyValuedProduct();
		productWithPriceDiff.setProductPrice(BigDecimal.ZERO);
		when(productDataAccessRepository.findById(product.get().getProductId())).thenReturn(product);
		eCommerceServiceImpl.addProduct(productWithPriceDiff);
	}

	
	@Test
	public void getproductTest() throws ProductNotFoundECommerceException {
		
		Optional<Product> inputproduct = Optional.ofNullable(ProductMockFactory.getDummyValuedProduct());
		when(productDataAccessRepository.findById(inputproduct.get().getProductId())).thenReturn(inputproduct);
		Product productOp = eCommerceServiceImpl.getProduct(inputproduct.get().getProductId());
		assertNotNull(productOp);
		assertTrue(productOp.equals(productOp));
		assertEquals(inputproduct.get().getProductId(), productOp.getProductId());
	}
	
	@Test(expected = ProductNotFoundECommerceException.class)
	public void getproduct_ProductNotFoundECommerceExceptionTest() throws ProductNotFoundECommerceException {
		Optional<Product> inputproduct = Optional.ofNullable(null);
		when(productDataAccessRepository.findById(11)).thenReturn(inputproduct);
		eCommerceServiceImpl.getProduct(11);
	}
}
