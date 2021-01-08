package com.techm.proddiscount.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseProductDiscount {

	@JsonProperty("id")
	private String productId;

	private Long quantity;

	public ResponseProductDiscount() {
	}

	public ResponseProductDiscount(String productId, Long quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ResponseProductDiscount [productId=" + productId + ", quantity=" + quantity + "]";
	}

}
