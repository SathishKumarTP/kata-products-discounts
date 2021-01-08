package com.techm.proddiscount.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.techm.proddiscount.rest.model.RequestProductDiscount;
import com.techm.proddiscount.rest.model.ResponseProductDiscount;
import com.techm.proddiscount.services.ProductDiscountService;
import com.techm.proddiscount.services.ProductDiscountServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductDiscountRestController.class)
public class ProductDiscountRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductDiscountService productDiscountService;

	@InjectMocks
	private ProductDiscountServiceImpl productDiscountServiceImpl;

	@Test
	public void promotion_request_example_1_test() throws Exception {
		when(productDiscountService.getProductDiscounts(any(RequestProductDiscount.class)))
		.thenReturn(Arrays.asList(
				new ResponseProductDiscount("X", 3L),
				new ResponseProductDiscount("Y", 1L)));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/promotions").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(
						"{ \"products\":[ { \"id\":\"A\" }, { \"id\":\"B\" }, { \"id\":\"C\" }, { \"id\":\"C\" }, { \"id\":\"D\" } ] }");

		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("[ { \"quantity\": 3, \"id\": \"X\" }, { \"quantity\": 1, \"id\": \"Y\" } ]"))
				.andReturn();
	}

	@Test
	public void promotion_request_example_2_test() throws Exception {
		when(productDiscountService.getProductDiscounts(any(RequestProductDiscount.class)))
		.thenReturn(Arrays.asList(
				new ResponseProductDiscount("X", 0L),
				new ResponseProductDiscount("Y", 0L)));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/promotions").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"clientType\":\"retail\", \"products\":[ { \"id\":\"B\" } ] }");

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().json("[ { \"quantity\": 0, \"id\": \"X\" }, { \"quantity\": 0, \"id\": \"Y\" } ]"))
				.andReturn();
	}

	@Test
	public void promotion_request_example_3_test() throws Exception {
		when(productDiscountService.getProductDiscounts(any(RequestProductDiscount.class))).thenReturn(
				Arrays.asList(
						new ResponseProductDiscount("X", 2L),
						new ResponseProductDiscount("Y", 1L)));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/promotions").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(
						"{ \"clientType\":\"business\", \"products\":[ { \"id\":\"A\" }, { \"id\":\"C\" }, { \"id\":\"C\" }, { \"id\":\"D\" } ] }");

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().json("[ { \"quantity\": 2, \"id\": \"X\" }, { \"quantity\": 1, \"id\": \"Y\" } ]"))
				.andReturn();
	}

	@Test
	public void promotion_error_message_test() throws Exception {
		when(productDiscountService.getProductDiscounts(any(RequestProductDiscount.class))).thenReturn(
				Arrays.asList(
						new ResponseProductDiscount("X", 2L),
						new ResponseProductDiscount("Y", 1L)));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/promotions").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(
						"{ \"clientType\":\"business\", \"products\":[ { \"id\":\"A\" }, { \"id\":\"C\" }, { \"id\":\"C\" }, { \"id\":\"D\" } ] }");

		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().json("[ { \"quantity\": 2, \"id\": \"X\" }, { \"quantity\": 1, \"id\": \"Y\" } ]"))
				.andReturn();
	}
}
