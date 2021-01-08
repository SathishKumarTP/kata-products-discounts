package com.techm.proddiscount.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.techm.proddiscount.error.ProductsNotPermittedException;
import com.techm.proddiscount.rest.model.Products;
import com.techm.proddiscount.rest.model.RequestProductDiscount;
import com.techm.proddiscount.rest.model.ResponseProductDiscount;

@RunWith(SpringRunner.class)
public class ProductDiscountServiceImplTest {

	@InjectMocks
	private ProductDiscountServiceImpl productDiscountServiceImpl;

	@Test
	public void getProductDiscountsTest_basic() {
		RequestProductDiscount requestProductDiscount = new RequestProductDiscount(Arrays.asList(new Products("A"),
				new Products("B"), new Products("C"), new Products("C"), new Products("D")));

		List<ResponseProductDiscount> productDiscounts = productDiscountServiceImpl.getProductDiscounts(requestProductDiscount);

		assertEquals(new ResponseProductDiscount("X", 3L).toString(), productDiscounts.get(0).toString());
		assertEquals(new ResponseProductDiscount("Y", 1L).toString(), productDiscounts.get(1).toString());
	}

	@Test
	public void getProductDiscountsTest_ZeroProducts() {
		RequestProductDiscount requestProductDiscount = new RequestProductDiscount(Arrays.asList(new Products("B")));

		List<ResponseProductDiscount> productDiscounts = productDiscountServiceImpl.getProductDiscounts(requestProductDiscount);

		assertEquals(new ResponseProductDiscount("Y", 0L).toString(), productDiscounts.get(0).toString());
		assertEquals(new ResponseProductDiscount("X", 0L).toString(), productDiscounts.get(1).toString());
	}

	@Test
	public void getProductDiscountsTest_Error_Message() {
		RequestProductDiscount requestProductDiscount = new RequestProductDiscount(Arrays.asList(new Products("A"), new Products("A")));

		ProductsNotPermittedException notPermittedException = null;
		try {
			productDiscountServiceImpl.getProductDiscounts(requestProductDiscount);
		} catch (ProductsNotPermittedException exception) {
			notPermittedException = exception;
		}
		assertEquals("1", notPermittedException.getCode());
		assertEquals("Set of products not permitted", notPermittedException.getMessage());
		assertEquals("Customer cannot have more than one product A", notPermittedException.getReason());
	}

	@Test
	public void getProductByBusinessRules_test() {
		List<ResponseProductDiscount> productDiscounts = productDiscountServiceImpl.getProductByBusinessRules(getMockedProductCountMap());

		assertEquals(new ResponseProductDiscount("X", 3L).toString(), productDiscounts.get(0).toString());
		assertEquals(new ResponseProductDiscount("Y", 1L).toString(), productDiscounts.get(1).toString());
	}

	@Test
	public void withProductA_Test() {
		List<ResponseProductDiscount> productDiscounts = productDiscountServiceImpl.withProductA(getMockedProductCountMap(), 3L);

		assertEquals(new ResponseProductDiscount("X", 3L).toString(), productDiscounts.get(0).toString());
		assertEquals(new ResponseProductDiscount("Y", 1L).toString(), productDiscounts.get(1).toString());
	}

	@Test
	public void withOutProductA_Test() {
		List<ResponseProductDiscount> productDiscounts = productDiscountServiceImpl.withOutProductA(getMockedProductCountMap(), 3L);

		assertEquals(new ResponseProductDiscount("Y", 0L).toString(), productDiscounts.get(0).toString());
		assertEquals(new ResponseProductDiscount("X", 3L).toString(), productDiscounts.get(1).toString());
	}

	@Test
	public void productDCount_test() {
		Map<String, Long> productsCountMap = getMockedProductCountMap();

		// assertEquals(0L, productDiscountServiceImpl.productDCount(null));
		assertEquals("0", productDiscountServiceImpl.productDCount(null).toString());
		assertEquals("1", productDiscountServiceImpl.productDCount(productsCountMap).toString());

		productsCountMap.put("D", 0L);
		assertEquals("0", productDiscountServiceImpl.productDCount(productsCountMap).toString());

		productsCountMap.put("D", 3l);
		assertEquals("3", productDiscountServiceImpl.productDCount(productsCountMap).toString());
	}

	@Test
	public void validateProductsCountForProductA_Test() {
		Map<String, Long> productsCountMap = getMockedProductCountMap();

		assertEquals(true, productDiscountServiceImpl.validateProductsCountForProductA(productsCountMap));

		productsCountMap.put("A", 3L);
		assertEquals(false, productDiscountServiceImpl.validateProductsCountForProductA(productsCountMap));

		productsCountMap.remove("A");
		assertEquals(true, productDiscountServiceImpl.validateProductsCountForProductA(productsCountMap));
	}

	private Map<String, Long> getMockedProductCountMap() {
		Map<String, Long> productsCountMap = new HashMap<>();
		productsCountMap.put("A", 1L);
		productsCountMap.put("B", 1L);
		productsCountMap.put("C", 2L);
		productsCountMap.put("D", 1L);
		return productsCountMap;
	}

}
