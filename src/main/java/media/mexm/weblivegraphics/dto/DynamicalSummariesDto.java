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
package media.mexm.weblivegraphics.dto;

import java.util.List;

public class DynamicalSummariesDto {

	private List<DynamicalSummaryDto> summaryList;
	private int selected;

	public List<DynamicalSummaryDto> getSummaryList() {
		return summaryList;
	}

	public void setSummaryList(final List<DynamicalSummaryDto> summaryList) {
		this.summaryList = summaryList;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(final int selected) {
		this.selected = selected;
	}

}
