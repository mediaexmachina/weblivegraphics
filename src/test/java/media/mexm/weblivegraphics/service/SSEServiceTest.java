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
package media.mexm.weblivegraphics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import media.mexm.weblivegraphics.SseEmitterPool;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

@SpringBootTest
@ActiveProfiles({ "SSEMock" })
class SSEServiceTest {

	@Autowired
	SSEService sseService;
	@Autowired
	SseEmitterPool sseEmitterPool;
	@Autowired
	OutputLayersDto layers;

	@BeforeEach
	void init() {
		assertTrue(MockUtil.isMock(sseEmitterPool));
		Mockito.reset(sseEmitterPool);
	}

	@AfterEach
	void ends() {
		verifyNoMoreInteractions(sseEmitterPool);
	}

	@Test
	void testSendLayersToFront() {
		sseService.sendLayersToFront();
		verify(sseEmitterPool, times(1)).send(layers);
	}

	@Test
	void testCreateFrontSSE() {
		final var emitter = Mockito.mock(SseEmitter.class);
		when(sseEmitterPool.create()).thenReturn(emitter);

		final var emitterReturn = sseService.createFrontSSE();

		assertEquals(emitter, emitterReturn);
		verify(sseEmitterPool, times(1)).create();
	}

}
