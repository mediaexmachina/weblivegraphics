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
package media.mexm.weblivegraphics.controller;

import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.dto.GraphicItemDto;
import media.mexm.weblivegraphics.dto.GraphicKeyerDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;
import media.mexm.weblivegraphics.service.GraphicService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({ "GraphicServiceMock" })
class LayersAPITest {

	private static final Faker faker = Faker.instance();
	private final ResultMatcher statusOk = status().isOk();

	@Autowired
	MockMvc mvc;
	@Autowired
	GraphicService graphicService;

	GraphicKeyerDto keyer;
	String label;
	UUID id;

	@BeforeEach
	void init() {
		assertTrue(MockUtil.isMock(graphicService));
		Mockito.reset(graphicService);

		label = faker.address().zipCode();
		keyer = new GraphicKeyerDto();
		id = UUID.randomUUID();
		keyer.setId(id);
		keyer.setLabel(label);

		when(graphicService.getKeyerByLabel(label)).thenReturn(keyer);
	}

	@AfterEach
	void ends() {
		Mockito.verifyNoMoreInteractions(graphicService);
	}

	@Test
	void testGetLayers() throws Exception {
		final var layers = new OutputLayersDto();
		when(graphicService.getLayers()).thenReturn(layers);

		mvc.perform(get("/v1/weblivegraphics/layers/")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.fullBypass").isBoolean());
		verify(graphicService, times(1)).getLayers();
	}

	@Test
	void testSwitchBypassOn() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/layers/bypass/on")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).setFullBypass(true);
	}

	@Test
	void testSwitchBypassOff() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/layers/bypass/off")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).setFullBypass(false);
	}

	@Test
	void testGetDSK() throws Exception {
		final var dsk = new GraphicKeyerDto();
		dsk.setId(UUID.randomUUID());
		dsk.setLabel("");

		when(graphicService.getDSK()).thenReturn(dsk);

		mvc.perform(get("/v1/weblivegraphics/layers/dsk")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.id").isString())
		        .andExpect(jsonPath("$.label").isString());
		verify(graphicService, times(1)).getDSK();
	}

	@Test
	void testSwitchDSKKeyerPgm() throws Exception {
		final var dsk = new GraphicKeyerDto();
		dsk.setId(UUID.randomUUID());
		when(graphicService.getDSK()).thenReturn(dsk);

		mvc.perform(put("/v1/weblivegraphics/layers/dsk/pgm?active=true")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).setActiveProgramKeyer(dsk.getId(), true);

		mvc.perform(put("/v1/weblivegraphics/layers/dsk/pgm?active=false")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).setActiveProgramKeyer(dsk.getId(), false);
		verify(graphicService, times(2)).getDSK();
	}

	@Test
	void testSwitchDSKKeyerPvw() throws Exception {
		final var dsk = new GraphicKeyerDto();
		dsk.setId(UUID.randomUUID());
		when(graphicService.getDSK()).thenReturn(dsk);

		mvc.perform(put("/v1/weblivegraphics/layers/dsk/pvw?active=true")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).setActivePreviewKeyer(dsk.getId(), true);

		mvc.perform(put("/v1/weblivegraphics/layers/dsk/pvw?active=false")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).setActivePreviewKeyer(dsk.getId(), false);
		verify(graphicService, times(2)).getDSK();
	}

	@Test
	void testSetDSKKeyerLabel() throws Exception {
		final var dsk = new GraphicKeyerDto();
		dsk.setId(UUID.randomUUID());
		when(graphicService.getDSK()).thenReturn(dsk);

		final var newLabel = faker.address().zipCode();
		mvc.perform(put("/v1/weblivegraphics/layers/dsk/label?label=" + newLabel)
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).setLabel(dsk.getId(), newLabel);
		verify(graphicService, times(1)).getDSK();
	}

	@Test
	void testGetKeyer() throws Exception {
		mvc.perform(get("/v1/weblivegraphics/layers/keyer?label=" + label)
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.id").isString())
		        .andExpect(jsonPath("$.label").value(label));
		verify(graphicService, times(1)).getKeyerByLabel(label);
	}

	@Test
	void testCreateKeyer() throws Exception {
		when(graphicService.addKeyer(label)).thenReturn(keyer);
		mvc.perform(post("/v1/weblivegraphics/layers/keyer?label=" + label)
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk)
		        .andExpect(jsonPath("$.id").isString())
		        .andExpect(jsonPath("$.label").value(label));
		verify(graphicService, times(1)).addKeyer(label);
	}

	@Test
	void testDeleteKeyer() throws Exception {
		mvc.perform(delete("/v1/weblivegraphics/layers/keyer?label=" + label)
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).getKeyerByLabel(label);
		verify(graphicService, times(1)).delete(id);
	}

	@Test
	void testSwitchKeyerPgm() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/layers/keyer/pgm?label=" + label + "&active=true")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).getKeyerByLabel(label);
		verify(graphicService, times(1)).setActiveProgramKeyer(id, true);
	}

	@Test
	void testSwitchKeyerPvw() throws Exception {
		mvc.perform(put("/v1/weblivegraphics/layers/keyer/pvw?label=" + label + "&active=true")
		        .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).getKeyerByLabel(label);
		verify(graphicService, times(1)).setActivePreviewKeyer(id, true);
	}

	@Nested
	class Item {

		GraphicItemDto item;
		String itemLabel;

		@BeforeEach
		void init() {
			item = new GraphicItemDto();
			itemLabel = faker.address().zipCode();
			item.setId(UUID.randomUUID());
			item.setLabel(itemLabel);
			keyer.setItems(List.of(item));
		}

		@Test
		void testGetItem() throws Exception {
			mvc.perform(get("/v1/weblivegraphics/layers/keyer/item?keyerLabel=" + label + "&itemLabel=" + itemLabel)
			        .accept(APPLICATION_JSON))
			        .andExpect(statusOk)
			        .andExpect(jsonPath("$.id").isString())
			        .andExpect(jsonPath("$.label").value(itemLabel));

			verify(graphicService, times(1)).getKeyerByLabel(label);
		}

		@Test
		void testGetItem_notFound() throws Exception {
			mvc.perform(get("/v1/weblivegraphics/layers/keyer/item?keyerLabel=nope&itemLabel=" + itemLabel)
			        .accept(APPLICATION_JSON))
			        .andExpect(status().isBadRequest());

			verify(graphicService, times(1)).getKeyerByLabel("nope");
		}

		@Test
		void testCreateItem() throws Exception {
			final var type = faker.address().zipCode();
			when(graphicService.addItem(id, itemLabel, type, true))
			        .thenReturn(item);
			item.setTypeName(type);

			mvc.perform(post("/v1/weblivegraphics/layers/keyer/item?keyerLabel=" + label
			                 + "&itemLabel=" + itemLabel
			                 + "&typeName=" + type
			                 + "&active=true").accept(APPLICATION_JSON))
			        .andExpect(statusOk)
			        .andExpect(jsonPath("$.id").isString())
			        .andExpect(jsonPath("$.label").value(itemLabel))
			        .andExpect(jsonPath("$.typeName").value(type));

			verify(graphicService, times(1)).getKeyerByLabel(label);
			verify(graphicService, times(1)).addItem(id, itemLabel, type, true);
		}

		@Test
		void testDeleteItem() throws Exception {
			mvc.perform(delete("/v1/weblivegraphics/layers/keyer/item?keyerLabel=" + label + "&itemLabel=" + itemLabel)
			        .accept(APPLICATION_JSON))
			        .andExpect(statusOk);

			verify(graphicService, times(1)).delete(item.getId());
			verify(graphicService, times(1)).getKeyerByLabel(label);
		}

		@Test
		void testSwitchItemActive() throws Exception {
			when(graphicService.setActiveItem(item.getId(), true)).thenReturn(item);
			mvc.perform(put("/v1/weblivegraphics/layers/keyer/item/active?keyerLabel=" + label
			                + "&itemLabel=" + itemLabel
			                + "&active=true").accept(APPLICATION_JSON))
			        .andExpect(statusOk)
			        .andExpect(jsonPath("$.id").isString())
			        .andExpect(jsonPath("$.label").value(itemLabel));

			verify(graphicService, times(1)).setActiveItem(item.getId(), true);
			verify(graphicService, times(1)).getKeyerByLabel(label);
		}

		@Test
		void testSetItemSetup() throws Exception {
			final var setup = Map.of("key", faker.cat().name());
			item.setSetup(setup);
			when(graphicService.setItemSetup(item.getId(), setup)).thenReturn(item);

			final var mapper = new ObjectMapper();
			mapper.configure(WRAP_ROOT_VALUE, false);
			final var ow = mapper.writer().withDefaultPrettyPrinter();
			final var requestJson = ow.writeValueAsString(setup);

			mvc.perform(put("/v1/weblivegraphics/layers/keyer/item/setup?keyerLabel="
			                + label + "&itemLabel=" + itemLabel)
			                        .contentType(APPLICATION_JSON)
			                        .accept(APPLICATION_JSON)
			                        .content(requestJson))
			        .andExpect(statusOk)
			        .andExpect(jsonPath("$.id").isString())
			        .andExpect(jsonPath("$.label").value(itemLabel));

			verify(graphicService, times(1)).setItemSetup(item.getId(), setup);
			verify(graphicService, times(1)).getKeyerByLabel(label);
		}

		@Test
		void testMoveItemInKeyer() throws Exception {
			when(graphicService.getKeyerByLabel("thenewkeyer")).thenReturn(keyer);
			final var layers = new OutputLayersDto();
			when(graphicService.getLayers()).thenReturn(layers);

			mvc.perform(post("/v1/weblivegraphics/layers/keyer/item/move?keyerLabel="
			                 + label + "&itemLabel=" + itemLabel + "&newKeyer=thenewkeyer")
			                         .accept(APPLICATION_JSON))
			        .andExpect(statusOk);

			verify(graphicService, times(1)).moveItemInKeyer(item.getId(), keyer);
			verify(graphicService, times(1)).getLayers();
			verify(graphicService, times(1)).getKeyerByLabel("thenewkeyer");
			verify(graphicService, times(1)).getKeyerByLabel(label);
		}
	}

	@Test
	void testMoveKeyer() throws Exception {
		final var pos = faker.random().nextInt(0, 1000000);
		mvc.perform(post("/v1/weblivegraphics/layers/keyer/move?label="
		                 + label + "&newPos=" + pos)
		                         .accept(APPLICATION_JSON))
		        .andExpect(statusOk);
		verify(graphicService, times(1)).moveKeyer(id, pos);
		verify(graphicService, times(1)).getLayers();
		verify(graphicService, times(1)).getKeyerByLabel(label);
	}
}
