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
package media.mexm.weblivegraphics.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class OutputLayersDtoTest {

	@Mock
	GraphicKeyerDto gKeyer;
	OutputLayersDto dto;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
		dto = new OutputLayersDto();
	}

	@AfterEach
	void end() {
		Mockito.verifyNoMoreInteractions(gKeyer);
	}

	@Test
	void testGetDownStreamKeyer() {
		assertNull(dto.getDownStreamKeyer());
	}

	@Test
	void testSetDownStreamKeyer() {
		dto.setDownStreamKeyer(gKeyer);
		assertEquals(gKeyer, dto.getDownStreamKeyer());
	}

	@Test
	void testGetKeyers() {
		assertNull(dto.getKeyers());
	}

	@Test
	void testSetKeyers() {
		dto.setKeyers(List.of(gKeyer));
		assertNotNull(dto.getKeyers());
		assertEquals(1, dto.getKeyers().size());
		assertEquals(gKeyer, dto.getKeyers().get(0));

	}

	@Test
	void testIsFullBypass() {
		assertFalse(dto.isFullBypass());
	}

	@Test
	void testSetFullBypass() {
		dto.setFullBypass(true);
		assertTrue(dto.isFullBypass());
	}
}
