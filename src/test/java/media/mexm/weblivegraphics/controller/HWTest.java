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
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
class HWTest {

	private static final MediaType TEXT_HTML_UTF8 = new MediaType("text", "html", UTF_8);

	private final ResultMatcher statusOk = status().isOk();
	private final ResultMatcher contentTypeHtmlUtf8 = content().contentType(TEXT_HTML_UTF8);
	private final ResultMatcher modelHasNoErrors = model().hasNoErrors();

	@Autowired
	private MockMvc mvc;

	@Test
	void testIndex() throws Exception {
		mvc.perform(get("/")
		        .accept(TEXT_HTML))
		        .andExpect(statusOk)
		        .andExpect(contentTypeHtmlUtf8)
		        .andExpect(modelHasNoErrors);
	}

}
