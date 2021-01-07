package com.techm.proddiscount.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.techm.proddiscount.rest.model.RequestProductDiscount;
import com.techm.proddiscount.rest.model.ResponseProductDiscount;

@Component
public class ProductDiscountServiceImpl implements ProductDiscountService {

	Logger logger = LoggerFactory.getLogger(ProductDiscountServiceImpl.class);

	@Override
	public List<ResponseProductDiscount> getProductDiscounts(RequestProductDiscount requestProductDiscount) {
		//TODO need to implement
		
		return new ArrayList<>();
	}
}