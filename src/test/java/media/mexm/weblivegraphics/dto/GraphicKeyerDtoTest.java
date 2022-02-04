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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

class GraphicKeyerDtoTest {

	private static final Faker faker = Faker.instance();
	GraphicKeyerDto dto;
	@Mock
	List<GraphicItemDto> items;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
		dto = new GraphicKeyerDto();
	}

	@AfterEach
	void end() {
		Mockito.verifyNoMoreInteractions(items);
	}

	@Test
	void testGetId() {
		assertNull(dto.getId());
	}

	@Test
	void testSetId() {
		final var uuid = UUID.randomUUID();
		dto.setId(uuid);
		assertEquals(uuid, dto.getId());
	}

	@Test
	void testGetLabel() {
		assertNull(dto.getLabel());
	}

	@Test
	void testSetLabel() {
		final var name = faker.name().fullName();
		dto.setLabel(name);
		assertEquals(name, dto.getLabel());
	}

	@Test
	void testGetItems() {
		assertNull(dto.getItems());
	}

	@Test
	void testSetItems() {
		dto.setItems(items);
		assertEquals(items, dto.getItems());
	}

	@Test
	void testIsActiveProgram() {
		assertFalse(dto.isActiveProgram());
	}

	@Test
	void testSetActiveProgram() {
		dto.setActiveProgram(true);
		assertTrue(dto.isActiveProgram());
	}

	@Test
	void testIsActivePreview() {
		assertFalse(dto.isActivePreview());
	}

	@Test
	void testSetActivePreview() {
		dto.setActivePreview(true);
		assertTrue(dto.isActivePreview());
	}

}
