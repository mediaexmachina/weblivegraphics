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
package media.mexm.weblivegraphics.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import media.mexm.weblivegraphics.dto.OutputLayersDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({ "StompMock" })
class FrontRestControllerTest {

	private final ResultMatcher statusOk = status().isOk();

	@Autowired
	MockMvc mvc;
	@Autowired
	OutputLayersDto layers;
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@BeforeEach
	void init() {
		assertTrue(MockUtil.isMock(simpMessagingTemplate));
		Mockito.reset(simpMessagingTemplate);
	}

	@AfterEach
	void ends() {
		Mockito.verifyNoMoreInteractions(simpMessagingTemplate);
	}

	@Test
	void testGetLayers() throws Exception {
		mvc.perform(get("/v1/weblivegraphics/front/refresh-layers")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(simpMessagingTemplate, times(1))
		        .convertAndSend("/topic/layers", layers);
	}
}
