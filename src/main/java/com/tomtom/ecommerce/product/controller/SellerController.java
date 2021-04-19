package com.tomtom.ecommerce.product.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomtom.ecommerce.product.builder.ECommerceResponseBuilder;
import com.tomtom.ecommerce.product.constants.ECommerceConstants;
import com.tomtom.ecommerce.product.model.Product;
import com.tomtom.ecommerce.product.model.ResponseStatus;
import com.tomtom.ecommerce.product.service.EcommerceProductService;
import com.tomtom.ecommerce.product.validator.ECommerceProductValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/seller")
public class SellerController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerController.class);

	@Autowired
	EcommerceProductService eCommerceService;
	
	@ApiOperation( value = "Adds new product or update quantity of existing one")
	@ApiResponses(value = {
			@ApiResponse(response = ECommerceConstants.class, message = "Success", code = 201)})
	@PostMapping("/product")
	public ResponseEntity<ResponseStatus> addProduct (@RequestBody @Valid Product product){
		try {
			eCommerceService.addProduct(product);
		} catch (Exception e) {
			LOGGER.warn("Exception occured while adding product : ", e);
			return ECommerceResponseBuilder.buildResponse(ECommerceConstants.FAILURE, HttpStatus.OK, e.getMessage());
		}
		return ECommerceResponseBuilder.buildResponse(ECommerceConstants.SUCCESS, HttpStatus.CREATED, (Object) null);
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
            binder.setValidator(new ECommerceProductValidator());

	}
}
