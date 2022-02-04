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
import java.util.UUID;

public class GraphicKeyerDto {

	private UUID id;
	private String label;
	private boolean activeProgram;
	private boolean activePreview;
	private List<GraphicItemDto> items;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public List<GraphicItemDto> getItems() {
		return items;
	}

	public void setItems(final List<GraphicItemDto> items) {
		this.items = items;
	}

	public boolean isActiveProgram() {
		return activeProgram;
	}

	public void setActiveProgram(final boolean activeProgram) {
		this.activeProgram = activeProgram;
	}

	public boolean isActivePreview() {
		return activePreview;
	}

	public void setActivePreview(final boolean activePreview) {
		this.activePreview = activePreview;
	}

}
