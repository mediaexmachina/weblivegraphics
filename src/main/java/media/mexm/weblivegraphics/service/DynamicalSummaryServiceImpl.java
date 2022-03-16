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
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import media.mexm.weblivegraphics.dto.DynamicalSummariesDto;
import media.mexm.weblivegraphics.dto.DynamicalSummaryDto;

@Service
public class DynamicalSummaryServiceImpl implements DynamicalSummaryService {
	private static final String CANT_FIND_SUMMARY_NAME = "Can't find summaryName \"";

	@Autowired
	private DynamicalSummariesDto dynamicalSummariesDto;

	@Override
	public List<DynamicalSummaryDto> getSummaryList() {
		return dynamicalSummariesDto.getSummaryList();
	}

	@Override
	public int getSelectedSummary() {
		return dynamicalSummariesDto.getSelected();
	}

	@Override
	public void createSummary(final String summaryName) {
		final var newSummary = new DynamicalSummaryDto();
		newSummary.setName(summaryName);
		newSummary.setSelected(-1);
		newSummary.setChapterList(List.of());

		final var summaryList = Optional.ofNullable(dynamicalSummariesDto.getSummaryList()).orElse(List.of());
		final var newList = Stream.concat(summaryList.stream(), Stream.of(newSummary))
		        .collect(toUnmodifiableList());
		dynamicalSummariesDto.setSummaryList(newList);
		dynamicalSummariesDto.setSelected(newList.size() - 1);
	}

	private Optional<DynamicalSummaryDto> getSummaryByName(final String summaryName) {
		return Optional.ofNullable(dynamicalSummariesDto.getSummaryList())
		        .stream()
		        .flatMap(List::stream)
		        .filter(s -> summaryName.equals(s.getName()))
		        .findFirst();
	}

	private DynamicalSummaryDto getSummaryByNameOrError(final String summaryName) {
		return getSummaryByName(summaryName)
		        .orElseThrow(() -> new IllegalArgumentException(CANT_FIND_SUMMARY_NAME + summaryName + "\""));
	}

	@Override
	public List<String> getChapters(final String summaryName) {
		return getSummaryByName(summaryName)
		        .map(DynamicalSummaryDto::getChapterList)
		        .orElse(List.of());
	}

	@Override
	public void setChapters(final String summaryName, final List<String> chapters) {
		final var summary = getSummaryByNameOrError(summaryName);
		summary.setChapterList(chapters.stream().collect(toUnmodifiableList()));
		summary.setSelected(-1);
	}

	@Override
	public void renameSummary(final String actualSummaryName, final String newSummaryName) {
		final var summary = getSummaryByNameOrError(actualSummaryName);
		summary.setName(newSummaryName);
	}

	@Override
	public void deleteSummary(final String summaryName) {
		final var newList = Optional.ofNullable(dynamicalSummariesDto.getSummaryList())
		        .stream()
		        .flatMap(List::stream)
		        .filter(Predicate.not(s -> summaryName.equals(s.getName())))
		        .collect(toUnmodifiableList());
		dynamicalSummariesDto.setSummaryList(newList);
	}

	@Override
	public List<String> listSummaries() {
		return Optional.ofNullable(dynamicalSummariesDto.getSummaryList())
		        .stream()
		        .flatMap(List::stream)
		        .map(DynamicalSummaryDto::getName)
		        .collect(toUnmodifiableList());
	}

	@Override
	public String getActiveSummary() {
		final var summaries = Optional.ofNullable(dynamicalSummariesDto.getSummaryList()).orElse(List.of());
		final var selected = dynamicalSummariesDto.getSelected();
		if (selected < 0 || selected + 1 > summaries.size()) {
			return null;
		}
		return summaries.get(selected).getName();
	}

	@Override
	public void setActiveSummary(final String summaryName) {
		final var summaries = Optional.ofNullable(dynamicalSummariesDto.getSummaryList()).orElse(List.of());

		for (var pos = 0; pos < summaries.size(); pos++) {
			if (summaryName.equals(summaries.get(pos).getName())) {
				dynamicalSummariesDto.setSelected(pos);
				return;
			}
		}
		throw new IllegalArgumentException(CANT_FIND_SUMMARY_NAME + summaryName + "\"");
	}

	@Override
	public String getActiveChapter(final String summaryName) {
		final var summary = getSummaryByNameOrError(summaryName);
		final var chapters = Optional.ofNullable(summary.getChapterList()).orElse(List.of());
		final var selected = summary.getSelected();
		if (selected < 0 || selected + 1 > chapters.size()) {
			return null;
		}
		return chapters.get(selected);

	}

	@Override
	public void setActiveChapter(final String summaryName, final int chapter) {
		final var summary = getSummaryByNameOrError(summaryName);
		final var chapters = Optional.ofNullable(summary.getChapterList()).orElse(List.of());
		final var maxChapter = chapters.size();

		if (maxChapter == 0) {
			summary.setSelected(-1);
		} else if (chapter > -1 && chapter < maxChapter) {
			summary.setSelected(chapter);
		}
	}

	@Override
	public void setNextChapterActive(final String summaryName) {
		final var summary = getSummaryByNameOrError(summaryName);
		final var chapters = Optional.ofNullable(summary.getChapterList()).orElse(List.of());
		final var selected = summary.getSelected();
		final var maxChapter = chapters.size();

		if (maxChapter == 0) {
			summary.setSelected(-1);
		} else {
			summary.setSelected(Math.min(selected + 1, maxChapter - 1));
		}
	}

	@Override
	public void setPreviousChapterActive(final String summaryName) {
		final var summary = getSummaryByNameOrError(summaryName);
		final var chapters = Optional.ofNullable(summary.getChapterList()).orElse(List.of());
		final var selected = summary.getSelected();
		final var maxChapter = chapters.size();

		if (maxChapter == 0) {
			summary.setSelected(-1);
		} else {
			summary.setSelected(Math.max(selected - 1, 0));
		}
	}

	@Override
	public void setFirstChapterActive(final String summaryName) {
		final var summary = getSummaryByNameOrError(summaryName);
		final var chapters = Optional.ofNullable(summary.getChapterList()).orElse(List.of());

		if (chapters.isEmpty()) {
			summary.setSelected(-1);
		} else {
			summary.setSelected(0);
		}
	}

	@Override
	public void setLastChapterActive(final String summaryName) {
		final var summary = getSummaryByNameOrError(summaryName);
		final var chapters = Optional.ofNullable(summary.getChapterList()).orElse(List.of());
		final var maxChapter = chapters.size();

		if (maxChapter == 0) {
			summary.setSelected(-1);
		} else {
			summary.setSelected(maxChapter - 1);
		}
	}

}
