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

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SSEService {

	void sendLayersToFront();

	SseEmitter createFrontSSE();

	void displaySummary();

	void hideSummary();

	void displayCurrentChapterCard();

	void hideCurrentChapterCard();

}
