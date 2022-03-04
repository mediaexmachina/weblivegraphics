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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import media.mexm.weblivegraphics.dto.OutputLayersDto;

@Service
public class StompServiceImpl implements StompService {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private OutputLayersDto layers;

	@Override
	public void sendLayersToFront() {
		log.debug("Do refresh layers to clients");
		template.convertAndSend("/topic/layers", layers);
	}
}
