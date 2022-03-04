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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;

import media.mexm.weblivegraphics.dto.OutputLayersDto;

@SpringBootTest
@ActiveProfiles({ "StompMock" })
class StompServiceTest {

	@Autowired
	StompService stompService;
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	OutputLayersDto layers;

	@BeforeEach
	void init() {
		assertTrue(MockUtil.isMock(simpMessagingTemplate));
		Mockito.reset(simpMessagingTemplate);
	}

	@AfterEach
	void ends() {
		verifyNoMoreInteractions(simpMessagingTemplate);
	}

	@Test
	void testSendLayersToFront() {
		stompService.sendLayersToFront();

		verify(simpMessagingTemplate, times(1))
		        .convertAndSend("/topic/layers", layers);
	}

}
