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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.dto.OutputLayersDto;

class SseEmitterPoolTest {
	private static final Faker faker = Faker.instance();

	SseEmitterPool pool;

	@Mock
	SseEmitter sseEmitter;
	@Mock
	ObjectMapper objectMapper;
	@Mock
	SseEventBuilder sseEventBuilder;
	@Captor
	ArgumentCaptor<Map> payloadCaptor;
	@Mock
	OutputLayersDto layersDto;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
		pool = new SseEmitterPool(() -> sseEmitter, objectMapper, () -> sseEventBuilder);

		when(sseEventBuilder.data(any())).thenReturn(sseEventBuilder);
		when(sseEventBuilder.id(any())).thenReturn(sseEventBuilder);
		when(sseEventBuilder.reconnectTime(anyLong())).thenReturn(sseEventBuilder);
		when(sseEventBuilder.name(any())).thenReturn(sseEventBuilder);
	}

	@AfterEach
	void ends() {
		Mockito.verifyNoMoreInteractions(sseEmitter, objectMapper, sseEventBuilder, layersDto);
	}

	@Test
	void testCreate() throws IOException {
		final var result = pool.create();
		assertNotNull(result);
		assertEquals(sseEmitter, result);

		verify(sseEmitter, times(1)).onCompletion(any());
		verify(sseEmitter, times(1)).onError(any());
		verify(sseEmitter, times(1)).onTimeout(any());
		verify(sseEventBuilder, times(1)).data(anyString());
		verify(sseEventBuilder, times(1)).id(anyString());
		verify(sseEventBuilder, times(1)).name(anyString());
		verify(sseEventBuilder, atMostOnce()).reconnectTime(anyLong());
		verify(sseEmitter, times(1)).send(sseEventBuilder);
	}

	@Test
	void testSend_layers() throws IOException {
		pool.create();
		Mockito.reset(sseEmitter, sseEventBuilder);

		when(sseEventBuilder.data(any(), any())).thenReturn(sseEventBuilder);
		when(sseEventBuilder.id(any())).thenReturn(sseEventBuilder);
		when(sseEventBuilder.reconnectTime(anyLong())).thenReturn(sseEventBuilder);
		when(sseEventBuilder.name(any())).thenReturn(sseEventBuilder);

		final var json = faker.address().fullAddress();
		when(objectMapper.writeValueAsString(any())).thenReturn(json);

		pool.send(layersDto);

		verify(sseEventBuilder, times(1)).data(json, APPLICATION_JSON);
		verify(sseEventBuilder, times(1)).id("0");
		verify(sseEventBuilder, times(1)).name("message");
		verify(sseEventBuilder, atMostOnce()).reconnectTime(anyLong());
		verify(sseEmitter, times(1)).send(sseEventBuilder);

		verify(objectMapper, times(1)).writeValueAsString(payloadCaptor.capture());
		final var payload = payloadCaptor.getValue();
		assertNotNull(payload);
		assertEquals(1, payload.size());
		assertTrue(payload.containsKey("layers"));
		assertEquals(layersDto, payload.get("layers"));
	}
}
