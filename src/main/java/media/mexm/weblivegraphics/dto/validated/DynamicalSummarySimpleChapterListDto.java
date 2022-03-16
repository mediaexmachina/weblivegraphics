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
package media.mexm.weblivegraphics.dto.validated;

import java.util.List;

import javax.validation.constraints.NotNull;

public class DynamicalSummarySimpleChapterListDto {

	@NotNull
	private List<String> chapters;

	public void setChapters(final List<String> chapters) {
		this.chapters = chapters;
	}

	public List<String> getChapters() {
		return chapters;
	}

}
