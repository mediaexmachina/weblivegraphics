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

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import media.mexm.weblivegraphics.dto.GraphicItemDto;
import media.mexm.weblivegraphics.dto.GraphicKeyerDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

@Service
public class GraphicServiceImpl implements GraphicService {
	private static final Logger log = LogManager.getLogger();
	private static final String CAN_T_FOUND_KEYER_WITH_UUID = "Can't found keyer with uuid \"";

	@Autowired
	private SSEService sSEService;
	@Autowired
	private OutputLayersDto layers;

	@Override
	public OutputLayersDto getLayers() {
		return layers;
	}

	@Override
	public void setFullBypass(final boolean bypass) {
		if (layers.isFullBypass() != bypass) {
			log.info("Set full bypass: {}", bypass);
			layers.setFullBypass(bypass);
			sSEService.sendLayersToFront();
		}
	}

	@Override
	public GraphicKeyerDto addKeyer(final String label) {
		final var keyer = new GraphicKeyerDto();
		keyer.setId(UUID.randomUUID());
		keyer.setLabel(label);

		if (layers.getKeyers() != null) {
			layers.setKeyers(Stream.concat(layers.getKeyers().stream(), Stream.of(keyer))
			        .collect(toUnmodifiableList()));
		} else {
			layers.setKeyers(List.of(keyer));
		}
		log.info("Add keyer: {}, {}", label, keyer.getId());
		sSEService.sendLayersToFront();
		return keyer;
	}

	private List<GraphicKeyerDto> getKeyers() {
		return Optional.ofNullable(layers.getKeyers()).orElse(List.of());
	}

	private Stream<GraphicKeyerDto> getKeyersAndDSK() {
		final var dsk = Optional.ofNullable(layers.getDownStreamKeyer()).stream();
		final var ks = getKeyers().stream();
		return Stream.concat(dsk, ks).filter(Objects::nonNull);
	}

	private Optional<GraphicKeyerDto> getKeyerByUUID(final UUID keyerUUID) {
		return getKeyersAndDSK()
		        .filter(k -> keyerUUID.equals(k.getId()))
		        .findFirst();
	}

	@Override
	public GraphicItemDto addItem(final UUID keyerUUID,
	                              final String label,
	                              final String typeName,
	                              final boolean active) {
		final var keyer = getKeyerByUUID(keyerUUID)
		        .orElseThrow(() -> new IllegalArgumentException(CAN_T_FOUND_KEYER_WITH_UUID + "\"" + keyerUUID + "\""));

		final var item = new GraphicItemDto();
		item.setId(UUID.randomUUID());
		item.setLabel(label);
		item.setTypeName(typeName);
		item.setActive(active);

		if (keyer.getItems() != null) {
			keyer.setItems(Stream.concat(keyer.getItems().stream(), Stream.of(item))
			        .collect(toUnmodifiableList()));
		} else {
			keyer.setItems(List.of(item));
		}

		log.info("Add {} Item: {} {} (on keyer {}/{}), active: {}",
		        typeName, label, item.getId(), keyerUUID, keyer.getLabel(), active);
		sSEService.sendLayersToFront();
		return item;
	}

	private Optional<GraphicItemDto> getItemByUUID(final UUID itemUUID) {
		return getKeyersAndDSK()
		        .filter(k -> k.getItems() != null)
		        .flatMap(k -> k.getItems().stream())
		        .filter(i -> itemUUID.equals(i.getId()))
		        .findFirst();
	}

	@Override
	public GraphicItemDto setActiveItem(final UUID itemUUID, final boolean active) {
		final var item = getItemByUUID(itemUUID)
		        .orElseThrow(() -> new IllegalArgumentException("Can't found item with UUID " + itemUUID));
		if (item.isActive() != active) {
			item.setActive(active);
			log.info("Set active item: {}, {}, {}", itemUUID, item.getLabel(), active);
			sSEService.sendLayersToFront();
		}
		return item;
	}

	@Override
	public void setActiveProgramKeyer(final UUID keyerUUID, final boolean active) {
		final var keyer = getKeyerByUUID(keyerUUID)
		        .orElseThrow(() -> new IllegalArgumentException(CAN_T_FOUND_KEYER_WITH_UUID + "\"" + keyerUUID + "\""));
		if (keyer.isActiveProgram() != active) {
			keyer.setActiveProgram(active);
			log.info("Set active keyer in Program: {}, {}, {}", keyerUUID, keyer.getLabel(), active);
			sSEService.sendLayersToFront();
		}
	}

	@Override
	public void setActivePreviewKeyer(final UUID keyerUUID, final boolean active) {
		final var keyer = getKeyerByUUID(keyerUUID)
		        .orElseThrow(() -> new IllegalArgumentException(CAN_T_FOUND_KEYER_WITH_UUID + keyerUUID));
		if (keyer.isActivePreview() != active) {
			keyer.setActivePreview(active);
			log.info("Set active keyer in Preview: {}, {}, {}", keyerUUID, keyer.getLabel(), active);
			sSEService.sendLayersToFront();
		}
	}

	@Override
	public GraphicItemDto setItemSetup(final UUID itemUUID, final Map<String, ?> setup) {
		final var item = getItemByUUID(itemUUID)
		        .orElseThrow(() -> new IllegalArgumentException("Can't found item with UUID " + itemUUID));

		final var actualContent = Optional.ofNullable(item.getSetup()).orElse(Map.of());
		final var newContent = setup.toString();

		if (actualContent.toString().equals(newContent) == false) {
			item.setSetup(setup);
			if (log.isDebugEnabled()) {
				log.debug("Set item setup: {}, {}, {}", itemUUID, item.getLabel(), newContent);
			} else if (log.isInfoEnabled()) {
				log.info("Set item setup: {}, {}", itemUUID, item.getLabel());
			}
			sSEService.sendLayersToFront();
		}
		return item;
	}

	@Override
	public GraphicKeyerDto getDSK() {
		if (layers.getDownStreamKeyer() == null) {
			final var keyer = new GraphicKeyerDto();
			keyer.setId(UUID.randomUUID());
			keyer.setLabel("DSK");
			layers.setDownStreamKeyer(keyer);
			sSEService.sendLayersToFront();
		}
		return layers.getDownStreamKeyer();
	}

	@Override
	public void setLabel(final UUID uuid, final String label) {
		final var ks = getKeyerByUUID(uuid).stream();
		final var it = getItemByUUID(uuid).stream();
		Stream.concat(ks, it)
		        .findFirst()
		        .orElseThrow(() -> new IllegalArgumentException(CAN_T_FOUND_KEYER_WITH_UUID + uuid + "\""))
		        .setLabel(label);
		sSEService.sendLayersToFront();
	}

	@Override
	public GraphicKeyerDto getKeyerByLabel(final String label) {
		return getKeyersAndDSK()
		        .filter(k -> label.equals(k.getLabel()))
		        .findFirst()
		        .orElseThrow(() -> new IllegalArgumentException("Can't found keyer with name \"" + label + "\""));
	}

	@Override
	public void delete(final UUID uuid) {
		final var dsk = Optional.ofNullable(layers.getDownStreamKeyer());
		if (dsk.isPresent() && uuid.equals(dsk.get().getId())) {
			layers.setDownStreamKeyer(null);
			sSEService.sendLayersToFront();
			return;
		}

		final var actualKeyers = Optional.ofNullable(layers.getKeyers())
		        .orElse(List.of());
		final var newKeyerList = actualKeyers.stream()
		        .filter(k -> uuid.equals(k.getId()) == false)
		        .collect(toUnmodifiableList());
		if (newKeyerList.size() != actualKeyers.size()) {
			layers.setKeyers(newKeyerList);
			sSEService.sendLayersToFront();
			return;
		}

		final var keyer = getKeyersAndDSK()
		        .filter(k -> k.getItems() != null)
		        .filter(k -> k.getItems().stream()
		                .anyMatch(i -> uuid.equals(i.getId())))
		        .findFirst()
		        .orElseThrow(() -> new IllegalArgumentException("Can't found keyer or item with uuid \"" + uuid
		                                                        + "\""));

		keyer.setItems(keyer.getItems().stream()
		        .filter(i -> uuid.equals(i.getId()) == false)
		        .collect(toUnmodifiableList()));
		sSEService.sendLayersToFront();
	}

	@Override
	public void moveKeyer(final UUID uuid, final int newPos) {
		final var keyers = layers.getKeyers();
		if (keyers == null) {
			throw new IllegalArgumentException("No keyers");
		} else if (keyers.size() == 1) {
			return;
		} else if (newPos < 0 || newPos >= keyers.size()) {
			throw new IndexOutOfBoundsException("Can't move to position to " + newPos);
		}

		final var keyerToMove = keyers.stream()
		        .filter(k -> k.getId().equals(uuid))
		        .findFirst()
		        .orElseThrow(() -> new IllegalArgumentException(CAN_T_FOUND_KEYER_WITH_UUID + uuid + "\""));

		final var cleanedKeyers = keyers.stream()
		        .filter(k -> k.equals(keyerToMove) == false)
		        .collect(toUnmodifiableList());

		if (newPos == 0) {
			layers.setKeyers(Stream.concat(Stream.of(keyerToMove), cleanedKeyers.stream())
			        .collect(toUnmodifiableList()));
		} else if (newPos + 1 == keyers.size()) {
			layers.setKeyers(Stream.concat(cleanedKeyers.stream(), Stream.of(keyerToMove))
			        .collect(toUnmodifiableList()));
		} else {
			final var listStart = cleanedKeyers.subList(0, newPos).stream();
			final var listEnd = cleanedKeyers.subList(newPos, cleanedKeyers.size()).stream();
			layers.setKeyers(Stream.of(listStart, Stream.of(keyerToMove), listEnd)
			        .flatMap(l -> l)
			        .collect(toUnmodifiableList()));
		}

		sSEService.sendLayersToFront();
	}

	@Override
	public void moveItemInKeyer(final UUID uuid, final GraphicKeyerDto keyer) {
		final Predicate<GraphicKeyerDto> haveUUIDItItem = k -> Optional.ofNullable(k.getItems())
		        .stream()
		        .flatMap(List::stream)
		        .anyMatch(i -> uuid.equals(i.getId()));

		final var keyerToEdit = getKeyersAndDSK()
		        .filter(haveUUIDItItem)
		        .findFirst()
		        .orElseThrow(() -> new IllegalArgumentException(CAN_T_FOUND_KEYER_WITH_UUID + uuid + "\""));

		if (keyerToEdit.equals(keyer)) {
			return;
		}

		final var itemToMove = keyerToEdit.getItems().stream()
		        .filter(k -> k.getId().equals(uuid))
		        .findFirst()
		        .orElseThrow(() -> new IllegalArgumentException(CAN_T_FOUND_KEYER_WITH_UUID + uuid + "\""));// SONAR WARNS...

		keyerToEdit.setItems(keyerToEdit.getItems()
		        .stream()
		        .filter(k -> k.equals(itemToMove) == false)
		        .collect(toUnmodifiableList()));

		final var keyerItemsStream = Optional.ofNullable(keyer.getItems()).orElse(List.of()).stream();
		final var itemToMoveStream = Stream.of(itemToMove);
		keyer.setItems(Stream.concat(keyerItemsStream, itemToMoveStream).collect(toUnmodifiableList()));

		sSEService.sendLayersToFront();
	}

}
