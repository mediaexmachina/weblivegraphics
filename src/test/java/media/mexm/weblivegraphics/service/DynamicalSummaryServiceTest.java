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
package media.mexm.weblivegraphics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.dto.DynamicalSummariesDto;
import media.mexm.weblivegraphics.dto.DynamicalSummaryDto;

@SpringBootTest
class DynamicalSummaryServiceTest {

	private static final Faker faker = Faker.instance();

	@Autowired
	DynamicalSummariesDto dynamicalSummariesDto;
	@Autowired
	DynamicalSummaryService dynamicalSummaryService;

	String summaryName;
	String chapterName;

	@Mock
	DynamicalSummaryDto summary;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();

		dynamicalSummariesDto.setSelected(0);
		dynamicalSummariesDto.setSummaryList(null);

		summaryName = faker.address().city();
		chapterName = faker.animal().name();
		when(summary.getName()).thenReturn(summaryName);
	}

	@AfterEach
	void end() {
		Mockito.verifyNoMoreInteractions(summary);
	}

	@Test
	void testCreateSummary() {
		dynamicalSummaryService.createSummary(summaryName);

		assertEquals(0, dynamicalSummariesDto.getSelected());
		final var sumList = dynamicalSummariesDto.getSummaryList();
		assertNotNull(sumList);
		assertEquals(1, sumList.size());
		final var s = sumList.get(0);
		assertEquals(summaryName, s.getName());
		assertEquals(List.of(), s.getChapterList());
		assertEquals(-1, s.getSelected());
	}

	@Test
	void testGetChapters() {
		dynamicalSummariesDto.setSummaryList(List.of(summary));

		var chapters = dynamicalSummaryService.getChapters(summaryName);
		assertEquals(List.of(), chapters);

		when(summary.getChapterList()).thenReturn(List.of(chapterName));

		chapters = dynamicalSummaryService.getChapters(summaryName);
		assertEquals(List.of(chapterName), chapters);
		verify(summary, atLeastOnce()).getName();
		verify(summary, atLeastOnce()).getChapterList();
	}

	@Test
	void testSetChapters() {
		dynamicalSummariesDto.setSummaryList(List.of(summary));
		final var chapters = List.of(chapterName);

		dynamicalSummaryService.setChapters(summaryName, chapters);
		verify(summary, times(1)).setChapterList(chapters);
		verify(summary, atLeastOnce()).getName();
		verify(summary, times(1)).setSelected(-1);
	}

	@Test
	void testRenameSummary() {
		final var newSummaryName = faker.ancient().hero();
		dynamicalSummariesDto.setSummaryList(List.of(summary));

		dynamicalSummaryService.renameSummary(summaryName, newSummaryName);
		verify(summary, times(1)).setName(newSummaryName);
		verify(summary, atLeastOnce()).getName();
	}

	@Test
	void testRenameSummary_cantFoundSummary() {
		final var badName = faker.ancient().hero();
		assertThrows(IllegalArgumentException.class, () -> dynamicalSummaryService.renameSummary(badName, ""));
	}

	@Test
	void testDeleteSummary() {
		final var toDeleteSummary = Mockito.mock(DynamicalSummaryDto.class);
		dynamicalSummariesDto.setSummaryList(List.of(summary, toDeleteSummary));
		when(summary.getName()).thenReturn("toKeep");
		when(toDeleteSummary.getName()).thenReturn("toDelete");

		dynamicalSummaryService.deleteSummary("toDelete");

		verify(summary, times(1)).getName();
		verify(toDeleteSummary, times(1)).getName();
		verifyNoMoreInteractions(toDeleteSummary);
		final var outList = dynamicalSummariesDto.getSummaryList();
		assertNotNull(outList);
		assertEquals(1, outList.size());
		assertEquals(summary, outList.get(0));
	}

	@Test
	void testSetActiveSummary_badName() {
		final var badName = faker.ancient().hero();
		assertThrows(IllegalArgumentException.class, () -> dynamicalSummaryService.setActiveSummary(badName));
	}

	@Nested
	class WithSummary {

		String summaryName;

		@BeforeEach
		void init() {
			summaryName = faker.address().city();
			dynamicalSummariesDto.setSummaryList(List.of(summary));
			when(summary.getName()).thenReturn(summaryName);
		}

		@AfterEach
		void end() {
			verify(summary, times(1)).getName();
		}

		@Test
		void testListSummaries() {
			final var outList = dynamicalSummaryService.listSummaries();

			assertNotNull(outList);
			assertEquals(1, outList.size());
			assertEquals(summaryName, outList.get(0));
		}

		@Test
		void testGetActiveSummary() {
			dynamicalSummariesDto.setSelected(0);

			final var name = dynamicalSummaryService.getActiveSummary();
			assertEquals(summaryName, name);

			dynamicalSummariesDto.setSelected(6);
			assertNull(dynamicalSummaryService.getActiveSummary());

			dynamicalSummariesDto.setSelected(-1);
			assertNull(dynamicalSummaryService.getActiveSummary());

			dynamicalSummariesDto.setSummaryList(null);
			dynamicalSummariesDto.setSelected(0);
			assertNull(dynamicalSummaryService.getActiveSummary());
		}

		@Test
		void testSetActiveSummary() {
			dynamicalSummariesDto.setSelected(-1);

			dynamicalSummaryService.setActiveSummary(summaryName);
			assertEquals(0, dynamicalSummariesDto.getSelected());
		}

		@Test
		void testGetActiveChapter() {
			final var chapterName = faker.animal().name();
			final var chapters = List.of(chapterName);
			when(summary.getChapterList()).thenReturn(chapters);
			when(summary.getSelected()).thenReturn(0);

			final var aChapter = dynamicalSummaryService.getActiveChapter(summaryName);
			assertEquals(chapterName, aChapter);
			verify(summary, times(1)).getChapterList();
			verify(summary, times(1)).getSelected();
		}

		@Test
		void testGetActiveChapter_invalidIndex() {
			final var chapterName = faker.animal().name();
			final var chapters = List.of(chapterName);
			when(summary.getChapterList()).thenReturn(chapters);
			when(summary.getSelected()).thenReturn(10);

			assertNull(dynamicalSummaryService.getActiveChapter(summaryName));
			verify(summary, times(1)).getChapterList();
			verify(summary, times(1)).getSelected();
		}

		@Nested
		class WithChapters {

			List<String> chapters;

			@BeforeEach
			void init() throws Exception {
				chapters = List.of(faker.address().city(), chapterName, faker.artist().name());
				when(summary.getChapterList()).thenReturn(chapters);
			}

			@AfterEach
			void ends() {
				verify(summary, times(1)).getChapterList();
			}

			@Test
			void testSetNextChapterActive() {
				when(summary.getSelected()).thenReturn(0);
				dynamicalSummaryService.setNextChapterActive(summaryName);
				verify(summary, times(1)).setSelected(1);
				verify(summary, times(1)).getSelected();
			}

			@Test
			void testSetNextChapterActive_noChapters() {
				when(summary.getSelected()).thenReturn(0);
				when(summary.getChapterList()).thenReturn(List.of());
				dynamicalSummaryService.setNextChapterActive(summaryName);
				verify(summary, times(1)).setSelected(-1);
				verify(summary, times(1)).getSelected();
			}

			@Test
			void testSetNextChapterActive_2() {
				when(summary.getSelected()).thenReturn(1);
				dynamicalSummaryService.setNextChapterActive(summaryName);
				verify(summary, times(1)).setSelected(2);
				verify(summary, times(1)).getSelected();
			}

			@Test
			void testSetPreviousChapterActive() {
				when(summary.getSelected()).thenReturn(0);
				dynamicalSummaryService.setPreviousChapterActive(summaryName);
				verify(summary, times(1)).setSelected(0);
				verify(summary, times(1)).getSelected();
			}

			@Test
			void testSetPreviousChapterActive_noChapters() {
				when(summary.getSelected()).thenReturn(0);
				when(summary.getChapterList()).thenReturn(List.of());
				dynamicalSummaryService.setPreviousChapterActive(summaryName);
				verify(summary, times(1)).setSelected(-1);
				verify(summary, times(1)).getSelected();
			}

			@Test
			void testSetPreviousChapterActive_1() {
				when(summary.getSelected()).thenReturn(2);
				dynamicalSummaryService.setPreviousChapterActive(summaryName);
				verify(summary, times(1)).setSelected(1);
				verify(summary, times(1)).getSelected();
			}

			@Test
			void testSetFirstChapterActive() {
				when(summary.getSelected()).thenReturn(0);
				dynamicalSummaryService.setFirstChapterActive(summaryName);
				verify(summary, times(1)).setSelected(0);
			}

			@Test
			void testSetFirstChapterActive_0() {
				when(summary.getSelected()).thenReturn(2);
				dynamicalSummaryService.setFirstChapterActive(summaryName);
				verify(summary, times(1)).setSelected(0);
			}

			@Test
			void testSetFirstChapterActive_noChapters() {
				when(summary.getSelected()).thenReturn(0);
				when(summary.getChapterList()).thenReturn(List.of());
				dynamicalSummaryService.setFirstChapterActive(summaryName);
				verify(summary, times(1)).setSelected(-1);
			}

			@Test
			void testSetLastChapterActive() {
				when(summary.getSelected()).thenReturn(0);
				dynamicalSummaryService.setLastChapterActive(summaryName);
				verify(summary, times(1)).setSelected(2);
			}

			@Test
			void testSetLastChapterActive_2() {
				when(summary.getSelected()).thenReturn(2);
				dynamicalSummaryService.setLastChapterActive(summaryName);
				verify(summary, times(1)).setSelected(2);
			}

			@Test
			void testSetLastChapterActive_noChapters() {
				when(summary.getSelected()).thenReturn(0);
				when(summary.getChapterList()).thenReturn(List.of());
				dynamicalSummaryService.setLastChapterActive(summaryName);
				verify(summary, times(1)).setSelected(-1);
			}
		}
	}

}
