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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class VendorSetupTest {

	@Test
	void testInitEmptyFile() throws IOException {
		final var temp = File.createTempFile("weblivegraphics-tests", ".properties");
		FileUtils.forceDelete(temp);
		new VendorSetup(temp);
		assertTrue(temp.exists());
		assertTrue(temp.isFile());
		final var content = FileUtils.readLines(temp, UTF_8);
		assertTrue(content.stream().allMatch(l -> l.startsWith("#")));
	}
}
