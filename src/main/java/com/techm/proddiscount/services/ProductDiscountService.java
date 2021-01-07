package com.techm.proddiscount.services;

import java.util.List;

import com.techm.proddiscount.rest.model.RequestProductDiscount;
import com.techm.proddiscount.rest.model.ResponseProductDiscount;

public interface ProductDiscountService {

	List<ResponseProductDiscount> getProductDiscounts(RequestProductDiscount requestProductDiscount);

}
