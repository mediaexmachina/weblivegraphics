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

import java.util.List;

import media.mexm.weblivegraphics.dto.DynamicalSummaryDto;

public interface DynamicalSummaryService {

	List<DynamicalSummaryDto> getSummaryList();

	int getSelectedSummary();

	void createSummary(String summaryName);

	List<String> getChapters(String summaryName);

	void setChapters(String summaryName, List<String> chapters);

	void renameSummary(String actualSummaryName, String newSummaryName);

	void deleteSummary(String summaryName);

	List<String> listSummaries();

	String getActiveSummary();

	void setActiveSummary(String summaryName);

	String getActiveChapter(String summaryName);

	void setNextChapterActive(String summaryName);

	void setPreviousChapterActive(String summaryName);

	void setFirstChapterActive(String summaryName);

	void setLastChapterActive(String summaryName);

	void setActiveChapter(String summaryName, int chapter);

}
