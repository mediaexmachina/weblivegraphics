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
package media.mexm.weblivegraphics.dto.ressource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

class DynamicalSummaryListItemDtoTest {

	@Mock
	List<String> chapters;
	String name;
	boolean selected;

	DynamicalSummaryListItemDto dto;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
		name = Faker.instance().ancient().god();
		selected = true;
		dto = new DynamicalSummaryListItemDto(name, selected, chapters);
	}

	@AfterEach
	void ends() {
		Mockito.verifyNoMoreInteractions(chapters);
	}

	@Test
	void testGetName() {
		assertEquals(name, dto.getName());
	}

	@Test
	void testIsSelected() {
		assertTrue(dto.isSelected());
	}

	@Test
	void testGetChapters() {
		assertEquals(chapters, dto.getChapters());
	}
}
