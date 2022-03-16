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
package media.mexm.weblivegraphics.dto.ressource;

import java.util.List;

public class DynamicalSummaryListItemDto {

	private final String name;
	private final boolean selected;
	private final List<String> chapters;

	public DynamicalSummaryListItemDto(final String name, final boolean selected, final List<String> chapters) {
		this.name = name;
		this.selected = selected;
		this.chapters = chapters;
	}

	public String getName() {
		return name;
	}

	public boolean isSelected() {
		return selected;
	}

	public List<String> getChapters() {
		return chapters;
	}
}
