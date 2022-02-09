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
package media.mexm.weblivegraphics.component;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import media.mexm.weblivegraphics.service.GraphicService;

@Component
public class AfterBoot { // TODO remove before merge
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private GraphicService graphicService;

	@PostConstruct
	void init() {
		log.info("Set demo configuration for layers");
		final var keyer = graphicService.addKeyer("Test keyer");
		final var item = graphicService.addItem(keyer.getId(), "Item test", "label", true);
		graphicService.setItemSetup(item.getId(), Map.of("text", "This is a simple label"));
		graphicService.setActiveProgramKeyer(keyer.getId(), true);
	}

}
