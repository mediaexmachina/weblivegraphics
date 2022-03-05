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
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import media.mexm.weblivegraphics.SseEmitterPool;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

@Service
public class SSEServiceImpl implements SSEService {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private OutputLayersDto layers;
	@Autowired
	private SseEmitterPool sseEmitterPool;

	@Override
	public void sendLayersToFront() {
		log.debug("Do refresh layers to clients");
		sseEmitterPool.send(layers);
	}

	@Override
	public SseEmitter createFrontSSE() {
		return sseEmitterPool.create();
	}

}
