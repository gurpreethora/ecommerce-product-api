package com.tomtom.ecommerce.product.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomtom.ecommerce.product.builder.ECommerceResponseBuilder;
import com.tomtom.ecommerce.product.constants.ECommerceConstants;
import com.tomtom.ecommerce.product.exception.NotFoundECommerceException;
import com.tomtom.ecommerce.product.model.Filter;
import com.tomtom.ecommerce.product.model.FilterBy;
import com.tomtom.ecommerce.product.model.Product;
import com.tomtom.ecommerce.product.model.ResponseStatus;
import com.tomtom.ecommerce.product.service.EcommerceProductService;
import com.tomtom.ecommerce.product.util.ECommerceServiceUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "ecommerce-product-api")
@RestController
@RequestMapping(value = "/ecommerce-product-api")
public class ECommerceProductController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ECommerceProductController.class);
	
	@Autowired
	EcommerceProductService eCommerceService;
	
	@ApiOperation( value = "Gets list of products")
	@ApiResponses(value = {
			@ApiResponse(response = Product[].class, message = ECommerceConstants.SUCCESS, code = 200)})
	@GetMapping (value = "/products/")
	public ResponseEntity<ResponseStatus> getProducts (){
		List<Product>  products;
		try {
			products =  eCommerceService.getproducts();
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.SUCCESS, HttpStatus.OK, products);
		} catch (NotFoundECommerceException e) {
			LOGGER.warn("No Products found : ", e);
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.FAILURE,HttpStatus.OK, e.getMessage());
		} catch (Exception e) {
			LOGGER.warn("Exception occured while getting products : ", e);
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.FAILURE,HttpStatus.OK, e.getMessage());
		}
		
	}
	
	@ApiOperation( value = "Gets list of products")
	@ApiResponses(value = {
			@ApiResponse(response = Product[].class, message = ECommerceConstants.SUCCESS, code = 200)})
	@GetMapping (value = "/filterProducts/")
	public ResponseEntity<ResponseStatus> getFilterProducts( @RequestParam(name = "filter", required=false) Filter filter,
			@RequestParam(name = "filterBy", required=false) FilterBy filterBy,
			@RequestParam(name = "maxPrice", required=false) BigDecimal maxPrice){
		List<Product>  products;
		try {
			products =  eCommerceService.getproducts();
			products = ECommerceServiceUtil.sortByFilter(products, filterBy, filter, maxPrice);
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.SUCCESS,HttpStatus.OK, products);
		} catch (NotFoundECommerceException e) {
			LOGGER.warn("No Products found : ", e);
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.FAILURE,HttpStatus.OK, e.getMessage());
		} catch (Exception e) {
			LOGGER.warn("Exception occured while getting products : ", e);
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.FAILURE,HttpStatus.OK, e.getMessage());
		}
	}
	
	@ApiOperation( value = "Gets product")
	@ApiResponses(value = {
			@ApiResponse(response = Product.class, message = ECommerceConstants.SUCCESS, code = 200)})
	@GetMapping (value = "/product/{productId}")
	public ResponseEntity<ResponseStatus> getProducts (@PathVariable @NotBlank @Size(min = 1, max = 50) Integer productId){
		Product product;
		try {
			product =  eCommerceService.getProduct(productId);
		} catch (Exception e) {
			LOGGER.warn("Exception occured while getting user cart : ", e);
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.FAILURE, HttpStatus.OK,  e.getMessage());
		}
		return ECommerceResponseBuilder.buildResponse(ECommerceConstants.SUCCESS, HttpStatus.OK, product);
	}
}
