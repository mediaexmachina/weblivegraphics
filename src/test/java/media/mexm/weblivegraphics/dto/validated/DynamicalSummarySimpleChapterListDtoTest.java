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
package media.mexm.weblivegraphics.dto.validated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class DynamicalSummarySimpleChapterListDtoTest {

	@Mock
	List<String> chapters;

	DynamicalSummarySimpleChapterListDto dto;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
		dto = new DynamicalSummarySimpleChapterListDto();
	}

	@AfterEach
	void ends() {
		Mockito.verifyNoMoreInteractions(chapters);
	}

	@Test
	void testSetChapters() {
		dto.setChapters(chapters);
		assertEquals(chapters, dto.getChapters());
	}

	@Test
	void testGetChapters() {
		assertNull(dto.getChapters());
	}
}
