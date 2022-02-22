/*
 * This file is part of weblivegraphics.
 *
 * weblivegraphics is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * weblivegraphics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * Copyright (C) Media ex Machina 2022
 *
 */
package media.mexm.weblivegraphics;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * See https://www.toptal.com/java/spring-boot-rest-api-error-handling
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger log = LogManager.getLogger();

	@ExceptionHandler(CantFoundItemException.class)
	protected ResponseEntity<Object> handleCantFoundItemException(final CantFoundItemException e,
	                                                              final WebRequest request) {
		log.warn("REST Error, can't found item", e);
		return new ResponseEntity<>(Map.of("message",
		        Optional.ofNullable(e.getMessage()).orElse("(No message)")),
		        BAD_REQUEST);
	}

	public static class CantFoundItemException extends RuntimeException {
		public CantFoundItemException(final String message) {
			super(message);
		}
	}

}
