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

import java.util.Map;

import javax.validation.constraints.NotNull;

public class LayerItemSetupDto {

	@NotNull
	private Map<String, ?> setup;

	public void setSetup(final Map<String, ?> setup) {
		this.setup = setup;
	}

	public Map<String, ?> getSetup() {// NOSONAR S1452
		return setup;
	}

}
