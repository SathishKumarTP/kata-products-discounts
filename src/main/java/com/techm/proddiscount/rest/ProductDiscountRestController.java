package com.techm.proddiscount.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techm.proddiscount.rest.model.RequestProductDiscount;
import com.techm.proddiscount.rest.model.ResponseProductDiscount;
import com.techm.proddiscount.services.ProductDiscountService;

@RestController
public class ProductDiscountRestController {

	Logger logger = LoggerFactory.getLogger(ProductDiscountRestController.class);
	
	@Autowired
	private ProductDiscountService productDiscountService;
	
	@PostMapping("/promotions")
	public List<ResponseProductDiscount> promotions(@RequestBody RequestProductDiscount requestProductDiscount) {
		logger.info("Promotions started");

		List<ResponseProductDiscount> responseProductDiscounts = productDiscountService.getProductDiscounts(requestProductDiscount);

		return responseProductDiscounts;
	}

}
