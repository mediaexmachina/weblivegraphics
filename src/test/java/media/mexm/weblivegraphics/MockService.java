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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import media.mexm.weblivegraphics.service.GraphicService;
import media.mexm.weblivegraphics.service.SSEService;

public class MockService {

	@Configuration
	@Profile({ "SSEMock" })
	static class SseEmitterPoolMock {

		@Bean
		@Primary
		public SseEmitterPool getSseEmitterPoolMock() {
			return Mockito.mock(SseEmitterPool.class);
		}

	}

	@Configuration
	@Profile({ "GraphicServiceMock" })
	static class GraphicServiceMock {

		@Bean
		@Primary
		public GraphicService getGraphicService() {
			return Mockito.mock(GraphicService.class);
		}
	}

	/*
	 * =========
	 * TEST ZONE
	 * =========
	 */

	@SpringBootTest
	@ActiveProfiles({ "SSEMock" })
	static class TestSseEmitterPoolMock {
		@Autowired
		SseEmitterPool sseEmitterPool;

		@Test
		void test() {
			assertTrue(MockUtil.isMock(sseEmitterPool));
		}
	}

	@SpringBootTest
	@ActiveProfiles({ "GraphicServiceMock" })
	static class TestGraphicServiceMock {
		@Autowired
		GraphicService graphicService;

		@Test
		void test() {
			assertTrue(MockUtil.isMock(graphicService));
		}
	}

	@SpringBootTest
	static class TestNotMock {
		@Autowired
		SseEmitterPool sseEmitterPool;
		@Autowired
		GraphicService graphicService;
		@Autowired
		SSEService sSEService;

		@Test
		void test() {
			assertFalse(MockUtil.isMock(sseEmitterPool));
			assertFalse(MockUtil.isMock(graphicService));
			assertFalse(MockUtil.isMock(sSEService));
		}
	}

}
