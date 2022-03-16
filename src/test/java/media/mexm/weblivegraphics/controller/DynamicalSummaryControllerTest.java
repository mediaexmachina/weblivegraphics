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
package media.mexm.weblivegraphics.controller;

import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.dto.DynamicalSummaryDto;
import media.mexm.weblivegraphics.dto.validated.DynamicalSummarySimpleChapterListDto;
import media.mexm.weblivegraphics.service.DynamicalSummaryService;
import media.mexm.weblivegraphics.service.SSEService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({ "SSEServiceMock", "DynamicalSummaryServiceMock" })
class DynamicalSummaryControllerTest {

	private static final Faker faker = Faker.instance();
	private final ResultMatcher statusOk = status().isOk();
	private final ResultMatcher statusCreated = status().isCreated();
	private final ResultMatcher statusNoContent = status().isNoContent();

	@Autowired
	DynamicalSummaryService dynamicalSummaryService;
	@Autowired
	SSEService sseService;
	@Autowired
	MockMvc mvc;

	String summaryName;
	String chapter;

	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
		Mockito.reset(dynamicalSummaryService, sseService);

		summaryName = faker.address().city();
		chapter = faker.aviation().aircraft();
	}

	@AfterEach
	void ends() {
		verifyNoMoreInteractions(dynamicalSummaryService, sseService);
	}

	@Test
	void testGetSummaries() throws Exception {
		final var s = new DynamicalSummaryDto();
		s.setName(summaryName);
		s.setSelected(0);
		s.setChapterList(List.of(chapter));

		when(dynamicalSummaryService.getSummaryList()).thenReturn(List.of(s));
		when(dynamicalSummaryService.getSelectedSummary()).thenReturn(0);

		mvc.perform(get("/v1/weblivegraphics/dynsummary")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.summaries[0].name", is(summaryName)))
		        .andExpect(jsonPath("$.summaries[0].selected", is(true)))
		        .andExpect(jsonPath("$.summaries[0].chapters", is(s.getChapterList())));

		verify(dynamicalSummaryService, times(1)).getSummaryList();
		verify(dynamicalSummaryService, times(1)).getSelectedSummary();
	}

	@Test
	void testCreateSummary() throws Exception {
		mvc.perform(post("/v1/weblivegraphics/dynsummary?summary=name&active=true")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusCreated);

		verify(dynamicalSummaryService, times(1)).createSummary("name");
		verify(dynamicalSummaryService, times(1)).setActiveSummary("name");
	}

	@Test
	void testDeleteSummary() throws Exception {
		mvc.perform(delete("/v1/weblivegraphics/dynsummary?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);

		verify(dynamicalSummaryService, times(1)).deleteSummary("name");
	}

	@Test
	void testGetChapters() throws Exception {
		final var chapters = List.of(chapter);
		when(dynamicalSummaryService.getChapters("name")).thenReturn(chapters);
		mvc.perform(get("/v1/weblivegraphics/dynsummary/chapters?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.summary", is("name")))
		        .andExpect(jsonPath("$.chapters", is(chapters)));

		verify(dynamicalSummaryService, times(1)).getChapters("name");
	}

	@Test
	void testSetChapters() throws Exception {
		when(dynamicalSummaryService.getActiveSummary()).thenReturn("name");

		final var mapper = new ObjectMapper();
		mapper.configure(WRAP_ROOT_VALUE, false);
		final var ow = mapper.writer().withDefaultPrettyPrinter();
		final var payload = new DynamicalSummarySimpleChapterListDto();
		payload.setChapters(List.of(faker.animal().name()));
		final var requestJson = ow.writeValueAsString(payload);

		mvc.perform(put("/v1/weblivegraphics/dynsummary/chapters?summary=name")
		        .contentType(APPLICATION_JSON)
		        .accept(APPLICATION_JSON)
		        .content(requestJson))
		        .andExpect(statusNoContent);

		verify(dynamicalSummaryService, times(1)).setChapters("name", payload.getChapters());
	}

	@Test
	void testSetActiveSummary() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/dynsummary/active?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);
		verify(dynamicalSummaryService, times(1)).setActiveSummary("name");
	}

	@Test
	void testGetActiveSummary() throws Exception {
		when(dynamicalSummaryService.getActiveSummary()).thenReturn(summaryName);

		mvc.perform(get("/v1/weblivegraphics/dynsummary/active")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.summary").value(summaryName));

		verify(dynamicalSummaryService, times(1)).getActiveSummary();
	}

	@Test
	void testGetActiveChapters() throws Exception {
		final var chapters = List.of(faker.animal().name());
		when(dynamicalSummaryService.getActiveSummary()).thenReturn(summaryName);
		when(dynamicalSummaryService.getChapters(summaryName)).thenReturn(chapters);

		mvc.perform(get("/v1/weblivegraphics/dynsummary/active/chapters")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.chapters", is(chapters)));

		verify(dynamicalSummaryService, times(1)).getChapters(summaryName);
		verify(dynamicalSummaryService, times(1)).getActiveSummary();
	}

	@Test
	void testGetActiveChapter() throws Exception {
		when(dynamicalSummaryService.getActiveChapter("name")).thenReturn(chapter);

		mvc.perform(get("/v1/weblivegraphics/dynsummary/active/chapter?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.summary", is("name")))
		        .andExpect(jsonPath("$.chapter", is(chapter)));

		verify(dynamicalSummaryService, times(1)).getActiveChapter("name");
	}

	@Test
	void testSetActiveChapter() throws Exception {
		when(dynamicalSummaryService.getActiveSummary()).thenReturn("name");

		final var pos = faker.random().nextInt(0, 1000);
		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/chapter?summary=name&chapter=" + pos)
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);

		verify(dynamicalSummaryService, times(1)).setActiveChapter("name", pos);
	}

	@Test
	void testSetActiveNextChapter() throws Exception {
		when(dynamicalSummaryService.getActiveSummary()).thenReturn("name");

		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/chapter/next?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);

		verify(dynamicalSummaryService, times(1)).setNextChapterActive("name");
	}

	@Test
	void testSetActivePrevChapter() throws Exception {
		when(dynamicalSummaryService.getActiveSummary()).thenReturn("name");

		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/chapter/previous?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);

		verify(dynamicalSummaryService, times(1)).setPreviousChapterActive("name");
	}

	@Test
	void testSetActiveFirstChapter() throws Exception {
		when(dynamicalSummaryService.getActiveSummary()).thenReturn("name");

		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/chapter/first?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);

		verify(dynamicalSummaryService, times(1)).setFirstChapterActive("name");
	}

	@Test
	void testSetActiveLastChapter() throws Exception {
		when(dynamicalSummaryService.getActiveSummary()).thenReturn("name");

		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/chapter/last?summary=name")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);

		verify(dynamicalSummaryService, times(1)).setLastChapterActive("name");
	}

	@Test
	void testSetShowActiveChapter() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/chapter/show")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);
		verify(sseService, times(1)).displayCurrentChapterCard();
	}

	@Test
	void testSetHideActiveChapter() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/chapter/hide")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);
		verify(sseService, times(1)).hideCurrentChapterCard();
	}

	@Test
	void testSetShowSummary() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/show")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);
		verify(sseService, times(1)).displaySummary();
	}

	@Test
	void testSetHideSummary() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/dynsummary/active/hide")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusNoContent);
		verify(sseService, times(1)).hideSummary();
	}
}
