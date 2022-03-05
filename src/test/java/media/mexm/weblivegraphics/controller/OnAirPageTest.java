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
 * Copyright (C) Media ex Machina 2021
 *
 */
package media.mexm.weblivegraphics.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import media.mexm.weblivegraphics.AppConf;
import media.mexm.weblivegraphics.SseEmitterPool;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({ "SSEMock" })
class OnAirPageTest {

	private static final MediaType TEXT_HTML_UTF8 = new MediaType("text", "html", UTF_8);

	private final ResultMatcher statusOk = status().isOk();
	private final ResultMatcher contentTypeHtmlUtf8 = content().contentType(TEXT_HTML_UTF8);
	private final ResultMatcher modelHasNoErrors = model().hasNoErrors();

	@Autowired
	private MockMvc mvc;
	@Autowired
	private AppConf conf;
	@Autowired
	private SseEmitterPool sseEmitterPool;

	@Value("${js.devmode:false}")
	private boolean devmode;

	@Test
	void testIndex() throws Exception {
		mvc.perform(get("/")
		        .accept(TEXT_HTML))
		        .andExpect(statusOk)
		        .andExpect(contentTypeHtmlUtf8)
		        .andExpect(modelHasNoErrors)
		        .andExpect(view().name("index"))
		        .andExpect(model().attribute("devmode", devmode));
	}

	@ParameterizedTest
	@ValueSource(strings = { "program", "clean", "preview" })
	void testScreenOutputs(final String pagekind) throws Exception {
		mvc.perform(get("/" + pagekind)
		        .accept(TEXT_HTML))
		        .andExpect(statusOk)
		        .andExpect(contentTypeHtmlUtf8)
		        .andExpect(modelHasNoErrors)
		        .andExpect(view().name("index"))
		        .andExpect(model().attribute("devmode", devmode))
		        .andExpect(model().attribute("pagekind", pagekind));
	}

	@Test
	void testGetBackgroundImage() throws Exception {
		final var result = mvc.perform(get("/background.png")
		        .accept(IMAGE_PNG))
		        .andExpect(statusOk)
		        .andExpect(content().contentType(IMAGE_PNG))
		        .andReturn();

		final var pngFile = FileUtils.readFileToByteArray(new File(conf.getBaseBackgroundFile()));
		assertTrue(Arrays.equals(pngFile, result.getResponse().getContentAsByteArray()));
	}

	@Test
	void testGetSSELayers() throws Exception {
		final var emitter = Mockito.mock(SseEmitter.class);
		when(sseEmitterPool.create()).thenReturn(emitter);

		mvc.perform(get("/sse"))
		        .andExpect(statusOk);

		verify(sseEmitterPool, times(1)).create();
	}

}
