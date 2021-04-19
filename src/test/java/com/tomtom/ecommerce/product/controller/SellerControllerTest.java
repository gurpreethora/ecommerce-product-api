package com.tomtom.ecommerce.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tomtom.ecommerce.product.controller.SellerController;
import com.tomtom.ecommerce.product.exception.NotFoundECommerceException;
import com.tomtom.ecommerce.product.exception.PriceMisMatchECommerceException;
import com.tomtom.ecommerce.product.mock.ProductMockFactory;
import com.tomtom.ecommerce.product.model.Product;
import com.tomtom.ecommerce.product.model.ResponseStatus;
import com.tomtom.ecommerce.product.service.EcommerceProductService;

@ExtendWith(MockitoExtension.class)
public class SellerControllerTest {
	
	@InjectMocks
	private SellerController sellerController;
	
	@Mock
	private EcommerceProductService commerceService ;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addProductTest() throws NotFoundECommerceException, PriceMisMatchECommerceException{
		Product product = ProductMockFactory.getDummyValuedProduct();
		Mockito.doNothing().when(commerceService).addProduct(product);
		ResponseEntity<ResponseStatus> respo = sellerController.addProduct(product);
		assertNotNull(respo);
		assertEquals(HttpStatus.CREATED, respo.getStatusCode());
	}
	
	@Test
	public void addProduct_PriceMisMatchECommerceExceptionTest() throws NotFoundECommerceException, PriceMisMatchECommerceException{
		Product product = ProductMockFactory.getDummyValuedProduct();
		doThrow(new PriceMisMatchECommerceException("Price Mismatch")).when(commerceService).addProduct(product);
		ResponseEntity<ResponseStatus> respo = sellerController.addProduct(product);
		assertNotNull(respo);
		assertEquals(HttpStatus.OK, respo.getStatusCode());
		assertEquals("Failure", respo.getBody().getStatus());
		assertFalse(respo.getBody().getMessages().isEmpty());
		assertEquals("Price Mismatch", respo.getBody().getMessages().get(0));
	}
}
