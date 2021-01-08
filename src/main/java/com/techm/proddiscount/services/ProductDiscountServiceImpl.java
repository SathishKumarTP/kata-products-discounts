package com.techm.proddiscount.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.techm.proddiscount.error.ProductsNotPermittedException;
import com.techm.proddiscount.rest.model.Products;
import com.techm.proddiscount.rest.model.RequestProductDiscount;
import com.techm.proddiscount.rest.model.ResponseProductDiscount;
import com.techm.proddiscount.util.Constants;

@Component
public class ProductDiscountServiceImpl implements ProductDiscountService {

	Logger logger = LoggerFactory.getLogger(ProductDiscountServiceImpl.class);

	@Override
	public List<ResponseProductDiscount> getProductDiscounts(RequestProductDiscount requestProductDiscount)
			throws ProductsNotPermittedException {
		Map<String, Long> productsCountMap = getProductCount(requestProductDiscount.getProducts());

		logger.debug("Product Details : " + productsCountMap);

		if (!validateProductsCountForProductA(productsCountMap)) {
			throw new ProductsNotPermittedException(Constants.code, Constants.message, Constants.reason);
		}
		return getProductByBusinessRules(productsCountMap);
	}

	public List<ResponseProductDiscount> getProductByBusinessRules(Map<String, Long> productsCountMap) {
		//Total number of product B and C
		Long productBC = (productsCountMap.get(Constants.product_B) != null ? productsCountMap.get(Constants.product_B) : 0)
				+ (productsCountMap.get(Constants.product_C) != null ? productsCountMap.get(Constants.product_C) : 0);

		// Has product A : False
		if (!productsCountMap.containsKey(Constants.product_A)) {
			return withOutProductA(productsCountMap, productBC);
		} else {
			// Has product A : True
			return withProductA(productsCountMap, productBC);
		}
	}

	public List<ResponseProductDiscount> withProductA(Map<String, Long> productsCountMap, Long productBC) {
		List<ResponseProductDiscount> productDiscounts = new ArrayList<>();
		if (productBC == 0) {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, 0L));
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_Y, productDCount(productsCountMap)));
		} else if (productBC == 1) {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, 1L));
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_Y, productDCount(productsCountMap)));
		} else if (productBC > 1) {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, productBC));
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_Y, productDCount(productsCountMap)));
		} else {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, 0L));
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_Y, productDCount(productsCountMap)));
		}
		return productDiscounts;
	}

	public List<ResponseProductDiscount> withOutProductA(Map<String, Long> productsCountMap, Long productBC) {
		List<ResponseProductDiscount> productDiscounts = new ArrayList<>();
		productDiscounts.add(new ResponseProductDiscount(Constants.discount_Y, 0L));
		if (productBC == 0) {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, productDCount(productsCountMap)));
		} else if (productBC == 1) {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, productDCount(productsCountMap)));
		} else if (productBC > 1) {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, productBC));
		} else {
			productDiscounts.add(new ResponseProductDiscount(Constants.discount_X, 0L));
		}
		return productDiscounts;
	}

	public Long productDCount(Map<String, Long> productsCount) {
		if (productsCount != null) {
			Long productDCount = productsCount.get(Constants.product_D) != null ? productsCount.get(Constants.product_D) : 0;

			if (productDCount == 0) {
				return 0L;
			} else if (productDCount == 1) {
				return 1L;
			} else if (productDCount > 1) {
				return productDCount;
			}
		}
		return 0L;
	}

	public boolean validateProductsCountForProductA(Map<String, Long> productsCount) {
		if (productsCount.get(Constants.product_A) == null || productsCount.get(Constants.product_A) <= 1)
			return true;
		return false;
	}

	public Map<String, Long> getProductCount(List<Products> list) {
		return list.stream().collect(Collectors.groupingBy(Products::getProductId, Collectors.counting()));
	}
}
