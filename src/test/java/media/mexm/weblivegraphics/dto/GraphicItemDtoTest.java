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

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

class GraphicItemDtoTest {

	private static final Faker faker = Faker.instance();
	GraphicItemDto dto;

	@BeforeEach
	void init() {
		dto = new GraphicItemDto();
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
	void testIsActive() {
		assertFalse(dto.isActive());
	}

	@Test
	void testSetActive() {
		dto.setActive(true);
		assertTrue(dto.isActive());
	}

	@Test
	void testGetTypeName() {
		assertNull(dto.getTypeName());
	}

	@Test
	void testSetTypeName() {
		final var name = faker.name().fullName();
		dto.setTypeName(name);
		assertEquals(name, dto.getTypeName());
	}

	@Test
	void testGetSetup() {
		assertNull(dto.getSetup());
	}

	@Test
	void testSetSetup() {
		final var setup = new HashMap<String, Object>();
		dto.setSetup(setup);
		assertEquals(setup, dto.getSetup());
	}
}
