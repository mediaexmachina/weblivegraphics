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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "weblivegraphics")
public class AppConf {

	private String vendorProps;
	private String baseBackgroundFile;

	public String getVendorProps() {
		return vendorProps;
	}

	public void setVendorProps(final String vendorProps) {
		this.vendorProps = vendorProps;
	}

	public String getBaseBackgroundFile() {
		return baseBackgroundFile;
	}

	public void setBaseBackgroundFile(final String baseBackgroundFile) {
		this.baseBackgroundFile = baseBackgroundFile;
	}

}
