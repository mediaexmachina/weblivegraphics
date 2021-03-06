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
package media.mexm.weblivegraphics.dto.ressource;

import java.util.List;

public class DynamicalSummaryListDto {

	private final List<DynamicalSummaryListItemDto> summaries;

	public DynamicalSummaryListDto(final List<DynamicalSummaryListItemDto> summaries) {
		this.summaries = summaries;
	}

	public List<DynamicalSummaryListItemDto> getSummaries() {
		return summaries;
	}
}
