package com.tomtom.ecommerce.product.model;

import java.util.List;

public class ResponseStatus {
	
	private String status;
	private List<String> messages;
	private List<Product> products;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
