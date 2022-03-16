/*
 * This file is part of weblivegraphics.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * Copyright (C) Media ex Machina 2022
 *
 */
package media.mexm.weblivegraphics.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import media.mexm.weblivegraphics.dto.ressource.DynamicalSummaryActiveChapterDto;
import media.mexm.weblivegraphics.dto.ressource.DynamicalSummaryActiveDto;
import media.mexm.weblivegraphics.dto.ressource.DynamicalSummaryChapterListDto;
import media.mexm.weblivegraphics.dto.ressource.DynamicalSummaryListDto;
import media.mexm.weblivegraphics.dto.ressource.DynamicalSummaryListItemDto;
import media.mexm.weblivegraphics.dto.validated.DynamicalSummarySimpleChapterListDto;
import media.mexm.weblivegraphics.service.DynamicalSummaryService;
import media.mexm.weblivegraphics.service.SSEService;

@RestController
@RequestMapping(value = "/v1/weblivegraphics/dynsummary", produces = APPLICATION_JSON_VALUE)
public class DynamicalSummaryController {

	@Autowired
	private DynamicalSummaryService dynamicalSummaryService;
	@Autowired
	private SSEService sseService;

	@GetMapping(value = "")
	public ResponseEntity<DynamicalSummaryListDto> getSummaries() {
		final var rawList = dynamicalSummaryService.getSummaryList();
		final var selected = dynamicalSummaryService.getSelectedSummary();
		final var summaries = new ArrayList<DynamicalSummaryListItemDto>(rawList.size());

		for (var pos = 0; pos < rawList.size(); pos++) {
			final var item = rawList.get(pos);
			summaries.add(new DynamicalSummaryListItemDto(item.getName(), selected == pos, item.getChapterList()));
		}
		final var result = new DynamicalSummaryListDto(summaries);
		return new ResponseEntity<>(result, OK);
	}

	@PostMapping(value = "")
	public ResponseEntity<Object> createSummary(@RequestParam(required = true) @NotEmpty final String summary,
	                                            @RequestParam(required = true) @NotEmpty final boolean active) {
		dynamicalSummaryService.createSummary(summary);
		if (active) {
			dynamicalSummaryService.setActiveSummary(summary);
		}
		return new ResponseEntity<>(CREATED);
	}

	@DeleteMapping(value = "")
	public ResponseEntity<Object> deleteSummary(@RequestParam(required = true) @NotEmpty final String summary) {
		dynamicalSummaryService.deleteSummary(summary);
		return new ResponseEntity<>(NO_CONTENT);
	}

	@GetMapping(value = "/chapters")
	public ResponseEntity<DynamicalSummaryChapterListDto> getChapters(@RequestParam final String summary) {
		final var chapters = dynamicalSummaryService.getChapters(summary);
		return new ResponseEntity<>(new DynamicalSummaryChapterListDto(summary, chapters), OK);
	}

	@PutMapping(value = "/chapters")
	public ResponseEntity<Object> setChapters(@RequestParam final String summary,
	                                          @RequestBody @Validated final DynamicalSummarySimpleChapterListDto chapterList) {
		dynamicalSummaryService.setChapters(summary, chapterList.getChapters());
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active")
	public ResponseEntity<Object> setActiveSummary(@RequestParam(required = true) @NotEmpty final String summary) {
		dynamicalSummaryService.setActiveSummary(summary);
		return new ResponseEntity<>(NO_CONTENT);
	}

	@GetMapping(value = "/active")
	public ResponseEntity<DynamicalSummaryActiveDto> getActiveSummary() {
		final var summary = dynamicalSummaryService.getActiveSummary();
		return new ResponseEntity<>(new DynamicalSummaryActiveDto(summary), OK);
	}

	@GetMapping(value = "/active/chapters")
	public ResponseEntity<DynamicalSummaryChapterListDto> getActiveChapters() {
		final var summary = dynamicalSummaryService.getActiveSummary();
		final var chapters = dynamicalSummaryService.getChapters(summary);
		return new ResponseEntity<>(new DynamicalSummaryChapterListDto(summary, chapters), OK);
	}

	@GetMapping(value = "/active/chapter")
	public ResponseEntity<DynamicalSummaryActiveChapterDto> getActiveChapter(@RequestParam final String summary) {
		final var activeChapter = dynamicalSummaryService.getActiveChapter(summary);
		return new ResponseEntity<>(new DynamicalSummaryActiveChapterDto(summary, activeChapter), OK);
	}

	@PutMapping(value = "/active/chapter")
	public ResponseEntity<Object> setActiveChapter(@RequestParam final String summary,
	                                               @RequestParam(required = true) final int chapter) {
		dynamicalSummaryService.setActiveChapter(summary, chapter);
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/chapter/next")
	public ResponseEntity<Object> setActiveNextChapter(@RequestParam final String summary) {
		dynamicalSummaryService.setNextChapterActive(summary);
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/chapter/previous")
	public ResponseEntity<Object> setActivePrevChapter(@RequestParam final String summary) {
		dynamicalSummaryService.setPreviousChapterActive(summary);
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/chapter/first")
	public ResponseEntity<Object> setActiveFirstChapter(@RequestParam final String summary) {
		dynamicalSummaryService.setFirstChapterActive(summary);
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/chapter/last")
	public ResponseEntity<Object> setActiveLastChapter(@RequestParam final String summary) {
		dynamicalSummaryService.setLastChapterActive(summary);
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/chapter/show")
	public ResponseEntity<Object> setShowActiveChapter() {
		sseService.displayCurrentChapterCard();
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/chapter/hide")
	public ResponseEntity<Object> setHideActiveChapter() {
		sseService.hideCurrentChapterCard();
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/show")
	public ResponseEntity<Object> setShowSummary() {
		sseService.displaySummary();
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping(value = "/active/hide")
	public ResponseEntity<Object> setHideSummary() {
		sseService.hideSummary();
		return new ResponseEntity<>(NO_CONTENT);
	}

}
