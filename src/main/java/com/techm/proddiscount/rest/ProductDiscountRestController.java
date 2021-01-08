package com.techm.proddiscount.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techm.proddiscount.error.CustomErrorResponse;
import com.techm.proddiscount.error.ProductsNotPermittedException;
import com.techm.proddiscount.rest.model.RequestProductDiscount;
import com.techm.proddiscount.rest.model.ResponseProductDiscount;
import com.techm.proddiscount.services.ProductDiscountService;

@RestController
public class ProductDiscountRestController {

	Logger logger = LoggerFactory.getLogger(ProductDiscountRestController.class);

	@Autowired
	private ProductDiscountService productDiscountService;

	@PostMapping("/promotions")
	public ResponseEntity<?> promotions(@RequestBody RequestProductDiscount requestProductDiscount) {
		logger.info("Promotions started");

		List<ResponseProductDiscount> responseProductDiscounts;
		try {
			responseProductDiscounts = productDiscountService.getProductDiscounts(requestProductDiscount);
			return ResponseEntity.ok(responseProductDiscounts);
		} catch (ProductsNotPermittedException exception) {
			logger.error("Promotions ended with exception : " + exception.getCode() + ":" + exception.getMessage());
			return new ResponseEntity<>(
					new CustomErrorResponse(exception.getCode(), exception.getMessage(), exception.getReason()),
					HttpStatus.BAD_REQUEST);
		} finally {
			logger.info("Promotions Ended");
		}
	}
}
