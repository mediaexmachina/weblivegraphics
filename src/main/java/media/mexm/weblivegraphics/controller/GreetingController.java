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
 * Copyright (C) Media ex Machina 2021
 *
 */
package media.mexm.weblivegraphics.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import media.mexm.weblivegraphics.stompdto.Greeting;
import media.mexm.weblivegraphics.stompdto.HelloMessage;

@Controller
public class GreetingController {

	private static final Logger log = LogManager.getLogger();

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(final HelloMessage message) throws InterruptedException {
		log.info("Message WS: {}", message.getName());
		Thread.sleep(100);
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + " !");
	}
}
