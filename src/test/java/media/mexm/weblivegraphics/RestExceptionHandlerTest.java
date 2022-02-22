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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.WebRequest;

import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.RestExceptionHandler.CantFoundItemException;

class RestExceptionHandlerTest {

	private static final Faker faker = Faker.instance();

	@Mock
	WebRequest request;

	RestExceptionHandler handler;
	CantFoundItemException e;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
		handler = new RestExceptionHandler();
		e = new CantFoundItemException(faker.buffy().quotes());
	}

	@AfterEach
	void ends() {
		Mockito.verifyNoMoreInteractions(request);
	}

	@Test
	void testHandleCantFoundItemException() {
		final var entity = handler.handleCantFoundItemException(e, request);

		assertNotNull(entity);
		assertEquals(BAD_REQUEST, entity.getStatusCode());
		final var body = (Map<?, ?>) entity.getBody();
		assertTrue(body.containsKey("message"));
		assertNotNull(body.get("message"));
		final var message = (String) body.get("message");
		assertEquals(e.getMessage(), message);
	}
}
