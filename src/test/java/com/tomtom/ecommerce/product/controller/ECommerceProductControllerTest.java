package com.tomtom.ecommerce.product.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tomtom.ecommerce.product.controller.ECommerceProductController;
import com.tomtom.ecommerce.product.exception.NotFoundECommerceException;
import com.tomtom.ecommerce.product.mock.ProductMockFactory;
import com.tomtom.ecommerce.product.model.Filter;
import com.tomtom.ecommerce.product.model.FilterBy;
import com.tomtom.ecommerce.product.model.Product;
import com.tomtom.ecommerce.product.model.ResponseStatus;
import com.tomtom.ecommerce.product.service.EcommerceProductService;
@ExtendWith(MockitoExtension.class)
public class ECommerceProductControllerTest {
	
	@InjectMocks
	private ECommerceProductController eCommerceController;
	
	@Mock
	private EcommerceProductService commerceService ;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductTest() throws NotFoundECommerceException{
		List<Product> lstProducts = ProductMockFactory.getProducts();
		when(commerceService.getproducts()).thenReturn(lstProducts);
		ResponseEntity<ResponseStatus> respo = eCommerceController.getProducts();
		assertNotNull(respo);
		assertEquals(2, respo.getBody().getProducts().size());
		assertEquals(HttpStatus.OK, respo.getStatusCode());
	}
	
	@Test
	public void product_NotFoundECommerceExceptionTest() throws NotFoundECommerceException{
		when(commerceService.getproducts()).thenThrow(new NotFoundECommerceException("Not Found"));
		ResponseEntity<ResponseStatus> respo = eCommerceController.getProducts();
		assertNotNull(respo);
		assertEquals(HttpStatus.OK, respo.getStatusCode());
		assertEquals("Failure", respo.getBody().getStatus());
		assertFalse(respo.getBody().getMessages().isEmpty());
		assertEquals("Not Found", respo.getBody().getMessages().get(0));
	}
	
	@Test
	public void getFilterProducts_Price_Test() throws NotFoundECommerceException{
		List<Product> lstProducts = ProductMockFactory.getProducts();
		when(commerceService.getproducts()).thenReturn(lstProducts);
		ResponseEntity<ResponseStatus> respo = eCommerceController.getFilterProducts(Filter.HIGH_TO_LOW,FilterBy.PRODUCT_PRICE, new BigDecimal(6));
		assertNotNull(respo);
		assertEquals(1, respo.getBody().getProducts().size());
		assertEquals(2, respo.getBody().getProducts().get(0).getProductId());
		assertEquals(HttpStatus.OK, respo.getStatusCode());
	}
	
	@Test
	public void getFilterProducts_Name_Test() throws NotFoundECommerceException{
		List<Product> lstProducts = ProductMockFactory.getProducts();
		when(commerceService.getproducts()).thenReturn(lstProducts);
		ResponseEntity<ResponseStatus> respo = eCommerceController.getFilterProducts(Filter.HIGH_TO_LOW, FilterBy.PRODUCT_NAME, new BigDecimal(6));
		assertNotNull(respo);
		assertEquals(1, respo.getBody().getProducts().size());
		assertEquals(2, respo.getBody().getProducts().get(0).getProductId());
		assertEquals(HttpStatus.OK, respo.getStatusCode());
	}

	@Test
	public void getFilterProducts_NotFoundECommerceExceptionTest() throws NotFoundECommerceException{
		when(commerceService.getproducts()).thenThrow(new NotFoundECommerceException("Not Found"));
		ResponseEntity<ResponseStatus> respo = eCommerceController.getFilterProducts(Filter.HIGH_TO_LOW,FilterBy.PRODUCT_NAME, new BigDecimal(6));
		assertNotNull(respo);
		assertEquals(HttpStatus.OK, respo.getStatusCode());
		assertEquals("Failure", respo.getBody().getStatus());
		assertFalse(respo.getBody().getMessages().isEmpty());
		assertEquals("Not Found", respo.getBody().getMessages().get(0));
	}
	
}
