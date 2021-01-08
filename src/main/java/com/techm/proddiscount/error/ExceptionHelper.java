package com.techm.proddiscount.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

	@ExceptionHandler(ProductsNotPermittedException.class)
	public ResponseEntity<Object> handleProductsNotPermittedException(ProductsNotPermittedException ex) {
		logger.error("Products Not Permitted Exception: ", ex.getMessage());
		return new ResponseEntity<Object>(new CustomErrorResponse("1", "Set of products not permitted",
				"Customer cannot have more than one product A"), HttpStatus.BAD_REQUEST);
	}
}
