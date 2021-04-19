package com.tomtom.ecommerce.product.validator;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tomtom.ecommerce.product.model.Product;

@Configurable
public class ECommerceProductValidator implements Validator {

	private static final String IS_MANDATORY = " is mandatory";
	private static final String EXPECTED_POSITIVE = " expected positive numeric value";
	private static final String EXPECTED_ALPHA_NUMERIC = " expected alpha-numeric value";
	private static final String PRODUCT_PRICE = "ProductPrice";
	private static final String PRODUCT_ID = "ProductId";
	private static final String PRODUCT_NAME = "ProductName";
	private static final String PRODUCT_QUANTITY = "ProductQuantity";
	

	@Override
	public boolean supports(Class<?> clazz) {

		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(target.getClass().equals(Product.class)) {
			validateProduct(target, errors);
		}
	}

	private void validateProduct(Object target, Errors errors) {
		Product product = (Product) target;
		if(product.getProductPrice() == null) {
			errors.rejectValue(PRODUCT_PRICE,"", IS_MANDATORY);
		}
			else if(product.getProductPrice()!= null && product.getProductPrice().compareTo(BigDecimal.ONE)<0) {
			errors.rejectValue(PRODUCT_PRICE,"", EXPECTED_POSITIVE);
		}
		
		if(StringUtils.isEmpty(product.getProductName())) {
			errors.rejectValue(PRODUCT_NAME, "" , IS_MANDATORY);
		}
		else if(!StringUtils.isEmpty(product.getProductName()) && product.getProductName().length()>50) {
			errors.rejectValue(PRODUCT_NAME, "", "can maxinum be 50 Characters");
		} else if(!StringUtils.isEmpty(product.getProductName()) && !StringUtils.isAlphanumeric(product.getProductName())) {
			errors.rejectValue(PRODUCT_NAME, "", EXPECTED_ALPHA_NUMERIC);
		}
		
		if(product.getProductQuantity() == null) {
			errors.rejectValue(PRODUCT_QUANTITY, "" , IS_MANDATORY);
		}else if(product.getProductQuantity()!= null && product.getProductQuantity()<1) {
			errors.rejectValue(PRODUCT_QUANTITY,"", EXPECTED_POSITIVE);
		}
		if(product.getProductId() == null) {
			errors.rejectValue(PRODUCT_ID, "" , IS_MANDATORY);
		}else if(product.getProductId()!= null && product.getProductId()<1) {
			errors.rejectValue(PRODUCT_ID,"", EXPECTED_POSITIVE);
		}
		
	}

}
