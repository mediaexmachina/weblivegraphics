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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import media.mexm.weblivegraphics.SseEmitterPool;
import media.mexm.weblivegraphics.dto.GraphicItemDto;
import media.mexm.weblivegraphics.dto.GraphicKeyerDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

@SpringBootTest
@ActiveProfiles({ "SSEMock" })
class GraphicServiceTest {

	private static final Faker faker = Faker.instance();

	@Autowired
	GraphicService graphicService;
	@Autowired
	OutputLayersDto layers;
	@Autowired
	SseEmitterPool sseEmitterPool;

	String keyerLabel;
	String itemLabel;
	String typeName;

	@BeforeEach
	void init() {
		layers.setDownStreamKeyer(null);
		layers.setFullBypass(false);
		layers.setKeyers(null);

		keyerLabel = faker.name().fullName();
		itemLabel = faker.name().fullName();
		typeName = faker.company().name();

		assertTrue(MockUtil.isMock(sseEmitterPool));
		Mockito.reset(sseEmitterPool);
	}

	@AfterEach
	void ends() {
		layers.setDownStreamKeyer(null);
		layers.setFullBypass(false);
		layers.setKeyers(null);

		Mockito.verifyNoMoreInteractions(sseEmitterPool);
	}

	void checkIsRefresh(final int count) {
		verify(sseEmitterPool, times(count)).send(layers);
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
		checkIsRefresh(2);
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
		checkIsRefresh(1);
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
		checkIsRefresh(2);
	}

	@Test
	void testAddItem_2items() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		final var addedItem0 = graphicService.addItem(addedKeyer.getId(), itemLabel, typeName, true);
		final var addedItem1 = graphicService.addItem(addedKeyer.getId(), itemLabel + "1", typeName + "1", true);

		checkAddKeyer(addedKeyer);
		assertFalse(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());

		assertNotNull(addedKeyer.getItems());
		assertEquals(2, addedKeyer.getItems().size());
		assertEquals(addedItem0, addedKeyer.getItems().get(0));
		assertEquals(addedItem1, addedKeyer.getItems().get(1));

		assertEquals(itemLabel, addedItem0.getLabel());
		assertEquals(itemLabel + "1", addedItem1.getLabel());
		assertEquals(typeName, addedItem0.getTypeName());
		assertEquals(typeName + "1", addedItem1.getTypeName());
		assertNotNull(addedItem0.getId());
		assertNotNull(addedItem1.getId());
		assertNotEquals(addedItem0.getId(), addedItem1.getId());
		assertTrue(addedItem0.isActive());
		assertTrue(addedItem1.isActive());

		assertNull(addedItem0.getSetup());
		assertNull(addedItem1.getSetup());
		checkIsRefresh(3);
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
		checkIsRefresh(3);
	}

	@Test
	void testSetActiveProgramKeyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActiveProgramKeyer(addedKeyer.getId(), true);
		checkAddKeyer(addedKeyer);
		assertTrue(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
		checkIsRefresh(2);
	}

	@Test
	void testSetActiveProgramKeyer_noChanges() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActiveProgramKeyer(addedKeyer.getId(), false);
		assertFalse(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
		checkIsRefresh(1);
	}

	@Test
	void testSetActivePreviewKeyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActivePreviewKeyer(addedKeyer.getId(), true);

		checkAddKeyer(addedKeyer);
		assertFalse(addedKeyer.isActiveProgram());
		assertTrue(addedKeyer.isActivePreview());
		checkIsRefresh(2);
	}

	@Test
	void testSetActivePreviewKeyer_noChanges() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActivePreviewKeyer(addedKeyer.getId(), false);
		assertFalse(addedKeyer.isActiveProgram());
		assertFalse(addedKeyer.isActivePreview());
		checkIsRefresh(1);
	}

	@Test
	void testSetActivePreviewAndProgramKeyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.setActiveProgramKeyer(addedKeyer.getId(), true);
		graphicService.setActivePreviewKeyer(addedKeyer.getId(), true);

		checkAddKeyer(addedKeyer);
		assertTrue(addedKeyer.isActiveProgram());
		assertTrue(addedKeyer.isActivePreview());
		checkIsRefresh(3);
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
		checkIsRefresh(3);
	}

	@Test
	void testSetItemSetup_noChanges() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		final var addedItem = graphicService.addItem(addedKeyer.getId(), itemLabel, typeName, true);

		final var json = new HashMap<String, Object>();
		json.put("someText", faker.aviation().airport());

		graphicService.setItemSetup(addedItem.getId(), json);
		graphicService.setItemSetup(addedItem.getId(), json);

		assertEquals(json, addedItem.getSetup());
		checkIsRefresh(3);
	}

	@Test
	void testGetDSK() {
		final var dsk = graphicService.getDSK();
		assertEquals(layers.getDownStreamKeyer(), dsk);
		checkIsRefresh(1);

		final var dsk2 = graphicService.getDSK();
		assertEquals(layers.getDownStreamKeyer(), dsk2);
	}

	@Test
	void testSetLabel_keyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);

		final var label = faker.animal().name();
		graphicService.setLabel(addedKeyer.getId(), label);

		assertEquals(label, addedKeyer.getLabel());
		checkIsRefresh(2);
	}

	@Test
	void testSetLabel_item() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		final var addedItem = graphicService.addItem(addedKeyer.getId(), itemLabel, typeName, true);

		final var label = faker.animal().name();
		graphicService.setLabel(addedItem.getId(), label);

		assertEquals(label, addedItem.getLabel());
		checkIsRefresh(3);
	}

	@Test
	void testSetLabel_DSK() {
		final var addedKeyer = graphicService.getDSK();

		final var label = faker.animal().name();
		graphicService.setLabel(addedKeyer.getId(), label);

		assertEquals(label, addedKeyer.getLabel());
		checkIsRefresh(2);
	}

	@Test
	void testGetKeyerByName_keyer() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);

		final var keyer = graphicService.getKeyerByLabel(addedKeyer.getLabel());
		assertEquals(addedKeyer, keyer);
		checkIsRefresh(1);
	}

	@Test
	void testGetKeyerByName_DSK() {
		final var addedKeyer = graphicService.getDSK();

		final var keyer = graphicService.getKeyerByLabel(addedKeyer.getLabel());
		assertEquals(addedKeyer, keyer);

		checkIsRefresh(1);
	}

	@Test
	void testDelete() {
		final var dsk = graphicService.getDSK();
		final var keyer0 = graphicService.addKeyer(keyerLabel);
		final var keyer1 = graphicService.addKeyer(keyerLabel);
		final var item = graphicService.addItem(keyer0.getId(), itemLabel, typeName, true);

		graphicService.delete(keyer1.getId());
		assertTrue(layers.getKeyers().contains(keyer0));
		assertTrue(layers.getKeyers().get(0).getItems().contains(item));
		assertFalse(layers.getKeyers().contains(keyer1));
		assertEquals(layers.getDownStreamKeyer(), dsk);

		graphicService.delete(item.getId());
		assertTrue(layers.getKeyers().contains(keyer0));
		assertTrue(layers.getKeyers().get(0).getItems().isEmpty());
		assertEquals(layers.getDownStreamKeyer(), dsk);

		graphicService.delete(keyer0.getId());
		assertFalse(layers.getKeyers().contains(keyer0));
		assertEquals(layers.getDownStreamKeyer(), dsk);

		graphicService.delete(dsk.getId());
		assertTrue(layers.getKeyers().isEmpty());
		assertNull(layers.getDownStreamKeyer());

		checkIsRefresh(8);
	}

	@Test
	void testMoveKeyer() {
		final var keyers = List.of(graphicService.addKeyer("k0"),
		        graphicService.addKeyer("k1"),
		        graphicService.addKeyer("k2"),
		        graphicService.addKeyer("k3"));
		assertEquals(keyers, layers.getKeyers());

		graphicService.moveKeyer(keyers.get(2).getId(), 1);

		assertEquals(keyers.get(0), layers.getKeyers().get(0));
		assertEquals(keyers.get(2), layers.getKeyers().get(1));
		assertEquals(keyers.get(1), layers.getKeyers().get(2));
		assertEquals(keyers.get(3), layers.getKeyers().get(3));

		graphicService.moveKeyer(keyers.get(1).getId(), 1);

		assertEquals(keyers.get(0), layers.getKeyers().get(0));
		assertEquals(keyers.get(1), layers.getKeyers().get(1));
		assertEquals(keyers.get(2), layers.getKeyers().get(2));
		assertEquals(keyers.get(3), layers.getKeyers().get(3));

		graphicService.moveKeyer(keyers.get(0).getId(), 3);

		assertEquals(keyers.get(1), layers.getKeyers().get(0));
		assertEquals(keyers.get(2), layers.getKeyers().get(1));
		assertEquals(keyers.get(3), layers.getKeyers().get(2));
		assertEquals(keyers.get(0), layers.getKeyers().get(3));

		graphicService.moveKeyer(keyers.get(2).getId(), 0);

		assertEquals(keyers.get(2), layers.getKeyers().get(0));
		assertEquals(keyers.get(1), layers.getKeyers().get(1));
		assertEquals(keyers.get(3), layers.getKeyers().get(2));
		assertEquals(keyers.get(0), layers.getKeyers().get(3));

		checkIsRefresh(8);
	}

	@Test
	void testMoveKeyer_oneKeyer() {
		final var addedKeyer0 = graphicService.addKeyer(keyerLabel);
		graphicService.moveKeyer(addedKeyer0.getId(), Integer.MAX_VALUE);
		checkIsRefresh(1);
	}

	@Test
	void testMoveKeyer_invalidKeyerPos() {
		final var addedKeyer0 = graphicService.addKeyer(keyerLabel);
		graphicService.addKeyer(keyerLabel);

		final var uuid = addedKeyer0.getId();
		assertThrows(IndexOutOfBoundsException.class,
		        () -> graphicService.moveKeyer(uuid, 2));
		assertThrows(IndexOutOfBoundsException.class,
		        () -> graphicService.moveKeyer(uuid, -1));
		checkIsRefresh(2);
	}

	@Test
	void testMoveItemInKeyer_1Item_keyer() {
		final var addedKeyer0 = graphicService.addKeyer(keyerLabel);
		final var addedKeyer1 = graphicService.addKeyer(keyerLabel);
		final var addedItem = graphicService.addItem(addedKeyer0.getId(), itemLabel, typeName, true);

		graphicService.moveItemInKeyer(addedItem.getId(), addedKeyer1);

		assertTrue(addedKeyer0.getItems().isEmpty());
		assertFalse(addedKeyer1.getItems().isEmpty());
		assertEquals(addedItem, addedKeyer1.getItems().get(0));

		graphicService.moveItemInKeyer(addedItem.getId(), addedKeyer0);
		assertFalse(addedKeyer0.getItems().isEmpty());
		assertTrue(addedKeyer1.getItems().isEmpty());
		assertEquals(addedItem, addedKeyer0.getItems().get(0));

		checkIsRefresh(5);
	}

	@Test
	void testMoveItemInKeyer_1Item_dsk() {
		final var addedKeyer = graphicService.addKeyer(keyerLabel);
		final var dsk = graphicService.getDSK();
		final var addedItem = graphicService.addItem(addedKeyer.getId(), itemLabel, typeName, true);

		graphicService.moveItemInKeyer(addedItem.getId(), dsk);

		assertTrue(addedKeyer.getItems().isEmpty());
		assertFalse(dsk.getItems().isEmpty());
		assertEquals(addedItem, dsk.getItems().get(0));

		graphicService.moveItemInKeyer(addedItem.getId(), addedKeyer);
		assertFalse(addedKeyer.getItems().isEmpty());
		assertTrue(dsk.getItems().isEmpty());
		assertEquals(addedItem, addedKeyer.getItems().get(0));

		checkIsRefresh(5);
	}

	@Test
	void testMoveItemInKeyer_nItems_keyer() {
		final var addedKeyer0 = graphicService.addKeyer(keyerLabel);
		final var addedKeyer1 = graphicService.addKeyer(keyerLabel);
		graphicService.addItem(addedKeyer0.getId(), itemLabel, typeName, true);
		final var addedItem = graphicService.addItem(addedKeyer0.getId(), itemLabel, typeName, true);
		graphicService.addItem(addedKeyer0.getId(), itemLabel, typeName, true);

		graphicService.moveItemInKeyer(addedItem.getId(), addedKeyer1);

		assertFalse(addedKeyer1.getItems().isEmpty());
		assertEquals(2, addedKeyer0.getItems().size());
		assertEquals(addedItem, addedKeyer1.getItems().get(0));

		graphicService.moveItemInKeyer(addedItem.getId(), addedKeyer0);
		assertEquals(3, addedKeyer0.getItems().size());
		assertTrue(addedKeyer1.getItems().isEmpty());
		assertEquals(addedItem, addedKeyer0.getItems().get(2));

		checkIsRefresh(7);
	}

	@Test
	void testMoveItemInKeyer_noChanges() {
		final var addedKeyer0 = graphicService.addKeyer(keyerLabel);
		final var addedItem = graphicService.addItem(addedKeyer0.getId(), itemLabel, typeName, true);

		graphicService.moveItemInKeyer(addedItem.getId(), addedKeyer0);
		assertFalse(addedKeyer0.getItems().isEmpty());
		assertEquals(addedItem, addedKeyer0.getItems().get(0));

		checkIsRefresh(2);
	}

	@Test
	void fail() {
		final var fakeUUID = UUID.randomUUID();
		final var fakeLabel = faker.ancient().hero();

		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.addItem(fakeUUID, null, null, false));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.setActiveItem(fakeUUID, false));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.setActiveProgramKeyer(fakeUUID, false));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.setActivePreviewKeyer(fakeUUID, false));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.setItemSetup(fakeUUID, null));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.setLabel(fakeUUID, null));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.getKeyerByLabel(fakeLabel));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.delete(fakeUUID));
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.moveKeyer(fakeUUID, 0));

		final var addeKeyer = graphicService.addKeyer(keyerLabel);
		graphicService.addKeyer(keyerLabel);
		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.moveKeyer(fakeUUID, 0));

		assertThrows(IllegalArgumentException.class,
		        () -> graphicService.moveItemInKeyer(fakeUUID, addeKeyer));

		checkIsRefresh(2);
	}

}
