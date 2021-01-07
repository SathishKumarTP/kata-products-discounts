package com.techm.proddiscount.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Products {

	@JsonProperty("id")
	private String productId;

	public Products(String productId) {
		this.productId = productId;
	}

	public Products() {
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
