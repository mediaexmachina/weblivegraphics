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

public class OutputLayersDto {

	private GraphicKeyerDto downStreamKeyer;
	private List<GraphicKeyerDto> keyers;
	private boolean fullBypass;

	public GraphicKeyerDto getDownStreamKeyer() {
		return downStreamKeyer;
	}

	public void setDownStreamKeyer(final GraphicKeyerDto downStreamKeyer) {
		this.downStreamKeyer = downStreamKeyer;
	}

	public List<GraphicKeyerDto> getKeyers() {
		return keyers;
	}

	public void setKeyers(final List<GraphicKeyerDto> keyers) {
		this.keyers = keyers;
	}

	public boolean isFullBypass() {
		return fullBypass;
	}

	public void setFullBypass(final boolean fullBypass) {
		this.fullBypass = fullBypass;
	}

}