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

import java.util.Map;
import java.util.UUID;

import media.mexm.weblivegraphics.dto.GraphicItemDto;
import media.mexm.weblivegraphics.dto.GraphicKeyerDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

public interface GraphicService {

	OutputLayersDto getLayers();

	void setFullBypass(boolean bypass);

	GraphicKeyerDto addKeyer(String label);

	GraphicKeyerDto getDSK();

	GraphicItemDto addItem(UUID keyer, String label, String typeName, boolean active);

	GraphicItemDto setActiveItem(final UUID item, final boolean active);

	void setActiveProgramKeyer(final UUID keyer, final boolean active);

	void setActivePreviewKeyer(final UUID keyer, final boolean active);

	GraphicItemDto setItemSetup(final UUID item, final Map<String, ?> setup);

	void refresh();

	void setLabel(UUID uuid, String label);

	GraphicKeyerDto getKeyerByLabel(String label);

	void delete(UUID id);

	void moveItemInKeyer(UUID id, GraphicKeyerDto keyer);

	void moveKeyer(UUID id, int newPos);
}
