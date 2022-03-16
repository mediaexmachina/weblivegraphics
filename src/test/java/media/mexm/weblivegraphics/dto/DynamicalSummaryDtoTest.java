/*
 * This file is part of weblivegraphics.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * Copyright (C) Media ex Machina 2022
 *
 */
package media.mexm.weblivegraphics.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

class DynamicalSummaryDtoTest {

	private static final Faker faker = Faker.instance();
	DynamicalSummaryDto dto;

	@BeforeEach
	void init() {
		dto = new DynamicalSummaryDto();
	}

	@Test
	void testGetName() {
		assertNull(dto.getName());
	}

	@Test
	void testSetName() {
		final var name = faker.animal().name();
		dto.setName(name);
		assertEquals(name, dto.getName());
	}

	@Test
	void testGetChapterList() {
		assertNull(dto.getChapterList());
	}

	@Test
	void testSetChapterList() {
		final var list = List.of(faker.animal().name());
		dto.setChapterList(list);
		assertEquals(list, dto.getChapterList());
	}

	@Test
	void testGetSelected() {
		assertEquals(0, dto.getSelected());
	}

	@Test
	void testSetSelected() {
		final var selected = faker.random().nextInt(10000);
		dto.setSelected(selected);
		assertEquals(selected, dto.getSelected());
	}
}
