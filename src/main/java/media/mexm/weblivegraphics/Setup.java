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
package media.mexm.weblivegraphics;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import media.mexm.weblivegraphics.dto.DynamicalSummariesDto;
import media.mexm.weblivegraphics.dto.OutputLayersDto;

@Configuration
@ComponentScan(basePackages = { "tv.hd3g.selfautorestdoc.mod" })
public class Setup {

	@Value("${weblivegraphics.sseTimeout:300s}")
	private Duration redirectTTL;

	@Bean
	public MessageSource messageSource() {
		return new ResourceBundleMessageSource();
	}

	@Bean
	public VendorSetup vendorSetup(@Autowired final AppConf appConf) throws IOException {
		return new VendorSetup(new File(appConf.getVendorProps()));
	}

	@Bean
	public OutputLayersDto getOutputLayers() {
		return new OutputLayersDto();
	}

	@Bean
	public DynamicalSummariesDto getDynamicalSummariesDto() {
		return new DynamicalSummariesDto();
	}

	@Bean
	public SseEmitterPool getSseEmitterPool() {
		return new SseEmitterPool(
		        () -> new SseEmitter(redirectTTL.toMillis()),
		        new ObjectMapper(),
		        SseEmitter::event);
	}
}
