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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import media.mexm.weblivegraphics.dto.GraphicItemDto;
import media.mexm.weblivegraphics.dto.GraphicKeyerDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

@Service
public class GraphicServiceImpl implements GraphicService {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private OutputLayersDto layers;
	@Autowired
	private SimpMessagingTemplate template;

	@Override
	public OutputLayersDto getLayers() {
		return layers;
	}

	@Override
	public void setFullBypass(final boolean bypass) {
		if (layers.isFullBypass() != bypass) {
			log.info("Set full bypass: {}", bypass);
			layers.setFullBypass(bypass);
			refresh();
		}
	}

	@Override
	public GraphicKeyerDto addKeyer(final String label) {
		final var keyer = new GraphicKeyerDto();
		keyer.setId(UUID.randomUUID());
		keyer.setLabel(label);

		final var keyers = Optional.ofNullable(layers.getKeyers())
		        .orElse(new ArrayList<>());
		keyers.add(keyer);
		layers.setKeyers(keyers);
		log.info("Add keyer: {}, {}", label, keyer.getId());
		return keyer;
	}

	private List<GraphicKeyerDto> getKeyers() {
		return Optional.ofNullable(layers.getKeyers())
		        .orElseThrow(() -> new NullPointerException("No keyers"));
	}

	private GraphicKeyerDto getKeyerByUUID(final UUID keyerUUID) {
		return getKeyers().stream()
		        .filter(k -> keyerUUID.equals(k.getId()))
		        .findFirst()
		        .orElseThrow(() -> new NullPointerException("Can't found keyer with UUID " + keyerUUID));
	}

	@Override
	public GraphicItemDto addItem(final UUID keyerUUID,
	                              final String label,
	                              final String typeName,
	                              final boolean active) {
		final var keyer = getKeyerByUUID(keyerUUID);

		final var items = Optional.ofNullable(keyer.getItems())
		        .orElse(new ArrayList<>());

		final var item = new GraphicItemDto();
		item.setId(UUID.randomUUID());
		item.setLabel(label);
		item.setTypeName(typeName);
		item.setActive(active);
		items.add(item);
		keyer.setItems(items);

		log.info("Add {} Item: {} {} (on keyer {}/{}), active: {}",
		        typeName, label, item.getId(), keyerUUID, keyer.getLabel(), active);
		refresh();
		return item;
	}

	private GraphicItemDto getItemByUUID(final UUID itemUUID) {
		return getKeyers().stream()
		        .filter(k -> k.getItems() != null)
		        .flatMap(k -> k.getItems().stream())
		        .filter(i -> itemUUID.equals(i.getId()))
		        .findFirst()
		        .orElseThrow(() -> new NullPointerException("Can't found item with UUID " + itemUUID));
	}

	@Override
	public void setActiveItem(final UUID itemUUID, final boolean active) {
		final var item = getItemByUUID(itemUUID);
		if (item.isActive() != active) {
			item.setActive(active);
			log.info("Set active item: {}, {}, {}", itemUUID, item.getLabel(), active);
			refresh();
		}
	}

	@Override
	public void setActiveProgramKeyer(final UUID keyerUUID, final boolean active) {
		final var keyer = getKeyerByUUID(keyerUUID);
		if (keyer.isActiveProgram() != active) {
			keyer.setActiveProgram(active);
			log.info("Set active keyer in Program: {}, {}, {}", keyerUUID, keyer.getLabel(), active);
			refresh();
		}
	}

	@Override
	public void setActivePreviewKeyer(final UUID keyerUUID, final boolean active) {
		final var keyer = getKeyerByUUID(keyerUUID);
		if (keyer.isActivePreview() != active) {
			keyer.setActivePreview(active);
			log.info("Set active keyer in Preview: {}, {}, {}", keyerUUID, keyer.getLabel(), active);
			refresh();
		}
	}

	@Override
	public void setItemSetup(final UUID itemUUID, final Map<String, Object> setup) {
		final var item = getItemByUUID(itemUUID);

		final var actualContent = Optional.ofNullable(item.getSetup()).orElse(Map.of());
		final var newContent = setup.toString();

		if (actualContent.toString().equals(newContent) == false) {
			item.setSetup(setup);
			if (log.isDebugEnabled()) {
				log.debug("Set item setup: {}, {}, {}", itemUUID, item.getLabel(), newContent);
			} else if (log.isInfoEnabled()) {
				log.info("Set item setup: {}, {}", itemUUID, item.getLabel());
			}
			refresh();
		}
	}

	@Override
	public void refresh() {
		log.debug("Do refresh layers to clients");
		template.convertAndSend("/topic/layers", layers);
	}
}
