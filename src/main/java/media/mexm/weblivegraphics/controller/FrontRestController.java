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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import media.mexm.weblivegraphics.service.StompService;

@RestController
@RequestMapping(value = "/v1/weblivegraphics/front", produces = APPLICATION_JSON_VALUE)
public class FrontRestController {

	@Autowired
	private StompService stompService;

	@GetMapping(value = "refresh-layers")
	public ResponseEntity<Object> getLayers() {
		stompService.sendLayersToFront();
		return new ResponseEntity<>(OK);
	}

}
