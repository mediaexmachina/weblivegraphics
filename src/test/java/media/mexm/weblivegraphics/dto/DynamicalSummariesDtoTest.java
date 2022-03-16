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

class DynamicalSummariesDtoTest {

	private static final Faker faker = Faker.instance();
	DynamicalSummariesDto dto;

	@BeforeEach
	void init() {
		dto = new DynamicalSummariesDto();
	}

	@Test
	void testGetSummaryList() {
		assertNull(dto.getSummaryList());
	}

	@Test
	void testSetSummaryList() {
		final var summaryList = List.of(new DynamicalSummaryDto());
		dto.setSummaryList(summaryList);
		assertEquals(summaryList, dto.getSummaryList());
	}

	@Test
	void testGetSelected() {
		assertEquals(0, dto.getSelected());
	}

	@Test
	void testSetSelected() {
		final var value = faker.random().nextInt(10000);
		dto.setSelected(value);
		assertEquals(value, dto.getSelected());
	}
}
