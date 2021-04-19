package com.tomtom.ecommerce.product.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.tomtom.ecommerce.product.model.Filter;
import com.tomtom.ecommerce.product.model.FilterBy;
import com.tomtom.ecommerce.product.model.Product;

public class ECommerceServiceUtil {

	private ECommerceServiceUtil() {
		
	}
	
	/**
	 * @param products
	 * @param filterBy
	 * @param filter
	 * @param maxPrice
	 * @return List<Product>
	 * This method takes list of product and sorts the list by Name/Price on basis od LOW-HIGH/HIGH-LOW
	 * The maxPrice param filters out the products with price less than or equal to maxPrice
	 */
	public static List<Product> sortByFilter(List<Product> products, FilterBy filterBy, Filter filter, BigDecimal maxPrice) {
		List<Product> lstproducts;
		if(filterBy!=null && filter!=null) {
			if(filterBy.equals(FilterBy.PRODUCT_NAME)) {
				if(filter.equals(Filter.LOW_TO_HIGH)) {
					products.sort((Product p1, Product p2)->p1.getProductName().compareTo(p2.getProductName())); 
				} else if(filter.equals(Filter.HIGH_TO_LOW)){
					products.sort((Product p1, Product p2)->p2.getProductName().compareTo(p1.getProductName())); 
				}
			} else if(filterBy.equals(FilterBy.PRODUCT_PRICE)) {
				if(filter.equals(Filter.LOW_TO_HIGH)) {
					products.sort((Product p1, Product p2)->p1.getProductPrice().compareTo(p2.getProductPrice()));
				} else if(filter.equals(Filter.HIGH_TO_LOW)){
					products.sort((Product p1, Product p2)->p2.getProductPrice().compareTo(p1.getProductPrice()));
				}
			}
		}
		lstproducts = products;
		if(maxPrice!=null) {
			lstproducts = products.stream().filter(p->p.getProductPrice().doubleValue()<=maxPrice.doubleValue()).collect(Collectors.toList());
		}
		return lstproducts;
	}

}
