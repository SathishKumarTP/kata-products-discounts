package com.techm.proddiscount.rest.model;

import java.util.List;

public class RequestProductDiscount {

	private List<Products> products;

	public RequestProductDiscount(List<Products> products) {
		this.products = products;
	}

	public RequestProductDiscount() {
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

}
