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
package media.mexm.weblivegraphics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.dto.GraphicItemDto;
import media.mexm.weblivegraphics.dto.GraphicKeyerDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;
import media.mexm.weblivegraphics.service.GraphicService;

@SpringBootTest
class GraphicServiceTest {

	private static final Faker faker = Faker.instance();

	@Autowired
	GraphicService graphicService;
	@Autowired
	OutputLayersDto layers;

	String keyerLabel;
	String itemLabel;
	String typeName;

	// TODO check sended events

	@BeforeEach
	void init() {
		layers.setDownStreamKeyer(null);
		layers.setFullBypass(false);
		layers.setKeyers(null);

		keyerLabel = faker.name().fullName();
		itemLabel = faker.name().fullName();
		typeName = faker.company().name();
	}

	@AfterEach
	void ends() {
		layers.setDownStreamKeyer(null);
		layers.setFullBypass(false);
		layers.setKeyers(null);
	}

	@Test
	void testGetLayers() {
		assertEquals(layers, graphicService.getLayers());
	}

	@Test
	void testSetFullBypass() {
		graphicService.setFullBypass(true);
		assertTrue(layers.isFullBypass());
		graphicService.setFullBypass(false);
		assertFalse(layers.isFullBypass());
	}

	private void checkAddKeyer(final GraphicKeyerDto addedKeyer) {
		assertNotNull(layers.getKeyers());
		assertEquals(1, layers.getKeyers().size());
		assertEquals(addedKeyer, layers.getKeyers().get(0));

		assertEquals(keyerLabel, addedKeyer.getLabel());
		assertNotNull(addedKeyer.getId());
	}

	@Test
	void testAddKeyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		assertNotNull(addedKeyer);

		checkAddKeyer(addedKeyer);
		assertFalse(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
		assertNull(addedKeyer.getItems());
	}

	private void checkAddItem(final GraphicKeyerDto addedKeyer, final GraphicItemDto addedItem) {
		assertNotNull(addedKeyer.getItems());
		assertEquals(1, addedKeyer.getItems().size());
		assertEquals(addedItem, addedKeyer.getItems().get(0));

		assertEquals(itemLabel, addedItem.getLabel());
		assertEquals(typeName, addedItem.getTypeName());
		assertNotNull(addedItem.getId());
		assertTrue(addedItem.isActive());
	}

	@Test
	void testAddItem() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		final var addedItem = graphicService.addItem(addedKeyer.getId(), itemLabel, typeName, true);

		checkAddKeyer(addedKeyer);
		assertFalse(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
		checkAddItem(addedKeyer, addedItem);
		assertNull(addedItem.getSetup());
	}

	@Test
	void testSetActiveItem() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		final var addedItem = graphicService.addItem(addedKeyer.getId(), itemLabel, typeName, true);

		graphicService.setActiveItem(addedItem.getId(), true);
		checkAddKeyer(addedKeyer);
		assertFalse(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
		checkAddItem(addedKeyer, addedItem);
		assertTrue(addedItem.isActive());
		assertNull(addedItem.getSetup());

		graphicService.setActiveItem(addedItem.getId(), false);
		assertFalse(addedItem.isActive());
	}

	@Test
	void testSetActiveProgramKeyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActiveProgramKeyer(addedKeyer.getId(), true);
		checkAddKeyer(addedKeyer);
		assertTrue(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
	}

	@Test
	void testSetActivePreviewKeyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActivePreviewKeyer(addedKeyer.getId(), true);

		checkAddKeyer(addedKeyer);
		assertFalse(addedKeyer.isActiveProgram());
		assertTrue(addedKeyer.isActivePreview());
	}

	@Test
	void testSetActivePreviewAndProgramKeyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActiveProgramKeyer(addedKeyer.getId(), true);
		graphicService.setActivePreviewKeyer(addedKeyer.getId(), true);

		checkAddKeyer(addedKeyer);
		assertTrue(addedKeyer.isActiveProgram());
		assertTrue(addedKeyer.isActivePreview());
	}

	@Test
	void testSetItemSetup() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		final var addedItem = graphicService.addItem(addedKeyer.getId(), itemLabel, typeName, true);

		final var json = new HashMap<String, Object>();
		json.put("someText", faker.aviation().airport());

		graphicService.setItemSetup(addedItem.getId(), json);

		checkAddKeyer(addedKeyer);
		assertFalse(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
		checkAddItem(addedKeyer, addedItem);
		assertTrue(addedItem.isActive());
		assertEquals(json, addedItem.getSetup());
	}

	@Test
	void testRefresh() {
		// TODO check refresh
	}
}
