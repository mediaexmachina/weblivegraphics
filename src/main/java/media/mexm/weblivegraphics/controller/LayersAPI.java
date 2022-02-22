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

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import media.mexm.weblivegraphics.RestExceptionHandler.CantFoundItemException;
import media.mexm.weblivegraphics.dto.GraphicItemDto;
import media.mexm.weblivegraphics.dto.GraphicKeyerDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;
import media.mexm.weblivegraphics.service.GraphicService;

@RestController
@RequestMapping(value = "/v1/weblivegraphics/layers", produces = APPLICATION_JSON_VALUE)
public class LayersAPI {

	@Autowired
	private GraphicService graphicService;

	@GetMapping(value = "/")
	public ResponseEntity<OutputLayersDto> getLayers() {
		return new ResponseEntity<>(graphicService.getLayers(), OK);
	}

	@PutMapping(value = "/bypass/on")
	public ResponseEntity<Object> switchBypassOn() {
		graphicService.setFullBypass(true);
		return new ResponseEntity<>(OK);
	}

	@PutMapping(value = "/bypass/off")
	public ResponseEntity<Object> switchBypassOff() {
		graphicService.setFullBypass(false);
		return new ResponseEntity<>(OK);
	}

	@GetMapping(value = "/dsk")
	public ResponseEntity<GraphicKeyerDto> getDSK() {
		final var dsk = graphicService.getDSK();
		return new ResponseEntity<>(dsk, OK);
	}

	@PutMapping(value = "/dsk/pgm")
	public ResponseEntity<Object> switchDSKKeyerPgm(@RequestParam final boolean active) {
		final var uuid = graphicService.getDSK().getId();
		graphicService.setActiveProgramKeyer(uuid, active);
		return new ResponseEntity<>(OK);
	}

	@PutMapping(value = "/dsk/pvw")
	public ResponseEntity<Object> switchDSKKeyerPvw(@RequestParam final boolean active) {
		final var uuid = graphicService.getDSK().getId();
		graphicService.setActivePreviewKeyer(uuid, active);
		return new ResponseEntity<>(OK);
	}

	@PutMapping(value = "/dsk/label")
	public ResponseEntity<Object> setDSKKeyerLabel(@RequestParam(required = true) @NotEmpty final String label) {
		final var uuid = graphicService.getDSK().getId();
		graphicService.setLabel(uuid, label);
		return new ResponseEntity<>(OK);
	}

	private GraphicKeyerDto getKeyerByName(@RequestParam(required = true) @NotEmpty final String label) {
		return Optional.ofNullable(graphicService.getKeyerByLabel(label))
		        .orElseThrow(() -> new CantFoundItemException("No keyer named as \"" + label + "\""));
	}

	@GetMapping(value = "/keyer")
	public ResponseEntity<GraphicKeyerDto> getKeyer(@RequestParam(required = true) @NotEmpty final String label) {
		final var keyer = getKeyerByName(label);
		return new ResponseEntity<>(keyer, OK);
	}

	private static String clean(final String userText) {
		return userText.replaceAll("[\n\r\t]", " ");
	}

	@PostMapping(value = "/keyer")
	public ResponseEntity<GraphicKeyerDto> createKeyer(@RequestParam(required = true) @NotEmpty final String label) {
		final var keyer = graphicService.addKeyer(clean(label));
		return new ResponseEntity<>(keyer, OK);
	}

	@DeleteMapping(value = "/keyer")
	public ResponseEntity<Object> deleteKeyer(@RequestParam(required = true) @NotEmpty final String label) {
		final var keyer = getKeyerByName(label);
		graphicService.delete(keyer.getId());
		return new ResponseEntity<>(OK);
	}

	@PutMapping(value = "/keyer/pgm")
	public ResponseEntity<Object> switchKeyerPgm(@RequestParam(required = true) @NotEmpty final String label,
	                                             @RequestParam(required = true) @NotEmpty final boolean active) {
		final var keyer = getKeyerByName(label);
		graphicService.setActiveProgramKeyer(keyer.getId(), active);
		return new ResponseEntity<>(OK);
	}

	@PutMapping(value = "/keyer/pvw")
	public ResponseEntity<Object> switchKeyerPvw(@RequestParam(required = true) @NotEmpty final String label,
	                                             @RequestParam(required = true) final boolean active) {
		final var keyer = getKeyerByName(label);
		graphicService.setActivePreviewKeyer(keyer.getId(), active);
		return new ResponseEntity<>(OK);
	}

	private GraphicItemDto getItemByNameAndByKeyerName(@RequestParam(required = true) @NotEmpty final String keyerLabel,
	                                                   @RequestParam(required = true) @NotEmpty final String itemLabel) {
		final var keyer = getKeyerByName(keyerLabel);
		final Supplier<CantFoundItemException> noItemException = () -> {
			final var message = "No item named as \"" + keyerLabel + "\" for keyer \"" + itemLabel + "\"";
			return new CantFoundItemException(message);
		};
		return Optional.ofNullable(keyer.getItems())
		        .orElseThrow(noItemException)
		        .stream()
		        .filter(i -> itemLabel.equals(i.getLabel()))
		        .findFirst()
		        .orElseThrow(noItemException);
	}

	@GetMapping(value = "/keyer/item")
	public ResponseEntity<GraphicItemDto> getItem(@RequestParam(required = true) @NotEmpty final String keyerLabel,
	                                              @RequestParam(required = true) @NotEmpty final String itemLabel) {
		final var item = getItemByNameAndByKeyerName(keyerLabel, itemLabel);
		return new ResponseEntity<>(item, OK);
	}

	@PostMapping(value = "/keyer/item")
	public ResponseEntity<GraphicItemDto> createItem(@RequestParam(required = true) @NotEmpty final String keyerLabel,
	                                                 @RequestParam(required = true) @NotEmpty final String itemLabel,
	                                                 @RequestParam(required = true) @NotEmpty final String typeName,
	                                                 @RequestParam(required = true) final boolean active) {
		final var keyer = getKeyerByName(keyerLabel);
		final var item = graphicService.addItem(keyer.getId(), clean(itemLabel), clean(typeName), active);
		return new ResponseEntity<>(item, OK);
	}

	@DeleteMapping(value = "/keyer/item")
	public ResponseEntity<Object> deleteItem(@RequestParam(required = true) @NotEmpty final String keyerLabel,
	                                         @RequestParam(required = true) @NotEmpty final String itemLabel) {
		final var item = getItemByNameAndByKeyerName(keyerLabel, itemLabel);
		graphicService.delete(item.getId());
		return new ResponseEntity<>(OK);
	}

	@PutMapping(value = "/keyer/item/active")
	public ResponseEntity<Object> switchItemActive(@RequestParam(required = true) @NotEmpty final String keyerLabel,
	                                               @RequestParam(required = true) @NotEmpty final String itemLabel,
	                                               @RequestParam(required = true) final boolean active) {
		final var item = getItemByNameAndByKeyerName(keyerLabel, itemLabel);
		final var updated = graphicService.setActiveItem(item.getId(), active);
		return new ResponseEntity<>(updated, OK);
	}

	@PutMapping(value = "/keyer/item/setup")
	public ResponseEntity<Object> setItemSetup(@RequestParam(required = true) @NotEmpty final String keyerLabel,
	                                           @RequestParam(required = true) @NotEmpty final String itemLabel,
	                                           @RequestBody final Map<String, ?> setup) {
		final var item = getItemByNameAndByKeyerName(keyerLabel, itemLabel);
		final var updated = graphicService.setItemSetup(item.getId(), setup);
		return new ResponseEntity<>(updated, OK);
	}

	@PostMapping(value = "/keyer/item/move")
	public ResponseEntity<OutputLayersDto> moveItemInKeyer(@RequestParam(required = true) @NotEmpty final String keyerLabel,
	                                                       @RequestParam(required = true) @NotEmpty final String itemLabel,
	                                                       @RequestParam(required = true) @NotEmpty final String newKeyer) {
		final var item = getItemByNameAndByKeyerName(keyerLabel, itemLabel);
		final var keyer = getKeyerByName(newKeyer);
		graphicService.moveItemInKeyer(item.getId(), keyer);
		return new ResponseEntity<>(graphicService.getLayers(), OK);
	}

	@PostMapping(value = "/keyer/move")
	public ResponseEntity<OutputLayersDto> moveKeyer(@RequestParam(required = true) @NotEmpty final String label,
	                                                 @RequestParam(required = true) final int newPos) {
		final var keyer = getKeyerByName(label);
		graphicService.moveKeyer(keyer.getId(), newPos);
		return new ResponseEntity<>(graphicService.getLayers(), OK);
	}

}
