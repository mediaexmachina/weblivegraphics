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

import static org.springframework.http.MediaType.IMAGE_PNG;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import media.mexm.weblivegraphics.AppConf;
import media.mexm.weblivegraphics.service.SSEService;

@Controller
public class OnAirPage {

	@Autowired
	private SSEService sseService;

	private static final String MODEL_INDEX = "index";

	@Value("${js.devmode:false}")
	private boolean devmode;
	@Autowired
	private AppConf appConf;

	@GetMapping("/")
	public String index(final Model model) {
		model.addAttribute("devmode", devmode);
		return MODEL_INDEX;
	}

	@GetMapping("/program")
	public String program(final Model model) {
		switchOutput("program", model);
		return MODEL_INDEX;
	}

	@GetMapping("/clean")
	public String clean(final Model model) {
		switchOutput("clean", model);
		return MODEL_INDEX;
	}

	@GetMapping("/preview")
	public String preview(final Model model) {
		switchOutput("preview", model);
		return MODEL_INDEX;
	}

	private void switchOutput(final String pagekind, final Model model) {
		model.addAttribute("devmode", devmode);
		model.addAttribute("pagekind", pagekind);
	}

	@GetMapping("/background.png")
	public ResponseEntity<InputStreamResource> getBackgroundImage() throws IOException {
		final var file = new File(appConf.getBaseBackgroundFile());
		final var resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity
		        .ok()
		        .contentType(IMAGE_PNG)
		        .contentLength(file.length())
		        .body(resource);
	}

	@GetMapping(value = "/sse")
	public SseEmitter getSSELayers() {
		return sseService.createFrontSSE();
	}

}
