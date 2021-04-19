package com.tomtom.ecommerce.product.validator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.validation.Errors;

import com.tomtom.ecommerce.product.model.Product;

public class ECommerceProductValidatorTest {

	Errors errors = mock(Errors.class);
	ECommerceProductValidator eCommerceValidator = new ECommerceProductValidator();
	
	private final String EXPECTED_POSITIVE_NUMERIC= " expected positive numeric value";
	private final String IS_MANDATORY = " is mandatory";
	private final String EXPECTED_ALPA_NUMERIC = " expected alpha-numeric value";
	
	@Test
	public void productMandatoryValidatortest() {
		eCommerceValidator.validate(new Product(), errors);
		verify(errors, times(1)).rejectValue("ProductPrice", "" , IS_MANDATORY);
		verify(errors, times(1)).rejectValue("ProductName", "" , IS_MANDATORY);
		verify(errors, times(1)).rejectValue("ProductQuantity", "" , IS_MANDATORY);
		verify(errors, times(1)).rejectValue("ProductId", "" , IS_MANDATORY);
		reset(errors);
	}
	
	@Test
	public void product_inValidInputs_Validatortest() {
		Errors errors = mock(Errors.class);
		Product product = new Product();
		product.setProductId(-1);
		product.setProductPrice(new BigDecimal(-1));
		product.setProductQuantity(-1);
		product.setProductName("%");
		eCommerceValidator.validate(product, errors);
		verify(errors, times(1)).rejectValue("ProductPrice", "" , EXPECTED_POSITIVE_NUMERIC);
		verify(errors, times(1)).rejectValue("ProductQuantity", "" , EXPECTED_POSITIVE_NUMERIC);
		verify(errors, times(1)).rejectValue("ProductId", "" , EXPECTED_POSITIVE_NUMERIC);
		verify(errors, times(1)).rejectValue("ProductName", "" , EXPECTED_ALPA_NUMERIC);
		reset(errors);
	}
}
