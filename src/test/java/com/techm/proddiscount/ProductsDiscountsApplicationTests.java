package com.techm.proddiscount;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductsDiscountsApplicationI {

	@Autowired
	private TestRestTemplate restTemplate;

	//TODO need to implement
	@Test
	public void contextLoads() {
		/*String response = this.restTemplate.getForObject("/promotions", String.class);
		assertEquals("Hellow World", response);*/
	}

}
