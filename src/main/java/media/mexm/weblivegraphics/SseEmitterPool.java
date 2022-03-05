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
package media.mexm.weblivegraphics;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import media.mexm.weblivegraphics.dto.OutputLayersDto;

public class SseEmitterPool {
	private static final Logger log = LogManager.getLogger();

	private final Supplier<SseEmitter> supplierSSEEmitter;
	private final ObjectMapper objectMapper;
	private final Supplier<SseEventBuilder> supplierSseEventBuilder;
	private final LinkedBlockingQueue<SseEmitter> emitterQueue;
	private final AtomicLong eventCounter;

	public SseEmitterPool(final Supplier<SseEmitter> supplierSSEEmitter,
	                      final ObjectMapper objectMapper,
	                      final Supplier<SseEventBuilder> supplierSseEventBuilder) {
		this.supplierSSEEmitter = supplierSSEEmitter;
		this.objectMapper = objectMapper;
		this.supplierSseEventBuilder = supplierSseEventBuilder;
		emitterQueue = new LinkedBlockingQueue<>();
		eventCounter = new AtomicLong();
	}

	public SseEmitter create() {
		final var emitter = supplierSSEEmitter.get();
		emitter.onCompletion(() -> emitterQueue.remove(emitter));
		emitter.onError(e -> log.debug("SSE error", e));
		emitter.onTimeout(() -> log.debug("Timeout for SSE"));

		try {
			emitter.send(supplierSseEventBuilder.get()
			        .data("nothing")
			        .id("init")
			        .reconnectTime(100)
			        .name("ping"));
		} catch (final IOException e) {
			log.debug("Send SSE ping event to client", e);
		}

		emitterQueue.add(emitter);
		return emitter;
	}

	private void send(final String name, final Object payload) {
		try {
			final var json = objectMapper.writeValueAsString(Map.of(name, payload));
			emitterQueue.stream()
			        .collect(toUnmodifiableList())
			        .parallelStream()
			        .forEach(em -> {
				        try {
					        final var id = String.valueOf(eventCounter.getAndIncrement());
					        log.debug("Send to event... {}", id);
					        final var event = supplierSseEventBuilder.get()
					                .reconnectTime(100)
					                .data(json, APPLICATION_JSON)
					                .id(id)
					                .name("message");
					        em.send(event);
				        } catch (final IOException e) {
					        log.debug("Can't send SSE event", e);
					        em.complete();
				        }
			        });
		} catch (final JsonProcessingException e) {
			log.warn("Can't format to JSON", e);
		}
	}

	public void send(final OutputLayersDto layers) {
		send("layers", layers);
	}
}
