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

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.SseEmitterPool;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

@SpringBootTest
@ActiveProfiles({ "SSEMock", "DynamicalSummaryServiceMock" })
class SSEServiceTest {
	private static final Faker faker = Faker.instance();

	@Autowired
	SSEService sseService;
	@Autowired
	SseEmitterPool sseEmitterPool;
	@Autowired
	DynamicalSummaryService dynamicalSummaryService;
	@Autowired
	OutputLayersDto layers;

	@BeforeEach
	void init() {
		assertTrue(MockUtil.isMock(sseEmitterPool));
		assertTrue(MockUtil.isMock(dynamicalSummaryService));
		Mockito.reset(sseEmitterPool, dynamicalSummaryService);
	}

	@AfterEach
	void ends() {
		verifyNoMoreInteractions(sseEmitterPool, dynamicalSummaryService);
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

	@Nested
	class Summary {

		String summary;
		String chapter;
		@Mock
		List<String> chapters;

		@BeforeEach
		void init() throws Exception {
			MockitoAnnotations.openMocks(this).close();

			summary = faker.address().city();
			when(dynamicalSummaryService.getActiveSummary()).thenReturn(summary);
			chapter = faker.cat().name();
			when(dynamicalSummaryService.getActiveChapter(summary)).thenReturn(chapter);
			when(dynamicalSummaryService.getChapters(summary)).thenReturn(chapters);
		}

		@Test
		void testDisplayCurrentChapterCard() {
			sseService.displayCurrentChapterCard();

			verify(dynamicalSummaryService, times(1)).getActiveSummary();
			verify(dynamicalSummaryService, times(1)).getActiveChapter(summary);
			verify(sseEmitterPool, times(1)).sendDynamicalSummaryChapter(chapter);
		}

		@Test
		void testDisplaySummary() {
			sseService.displaySummary();
			verify(dynamicalSummaryService, times(1)).getActiveSummary();
			verify(dynamicalSummaryService, times(1)).getChapters(summary);
			verify(sseEmitterPool, times(1)).sendDynamicalSummary(chapters);
		}

		@Test
		void testHideCurrentChapterCard() {
			sseService.hideCurrentChapterCard();
			verify(sseEmitterPool, times(1)).sendDynamicalSummaryChapter(null);
		}

		@Test
		void testHideSummary() {
			sseService.hideSummary();
			verify(sseEmitterPool, times(1)).sendDynamicalSummary(null);
		}
	}
}
