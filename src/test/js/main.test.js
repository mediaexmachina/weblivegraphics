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
'use strict';

import React from 'react';
import { create } from "react-test-renderer";

import { Main } from '../../main/js/main';

test('Check base program page', () => {
	// https://blog.logrocket.com/tdd-with-react-test-renderer/
  // https://fr.reactjs.org/docs/test-renderer.html

  const rendered = create(<Main />);
  //console.log(rendered.toJSON());
  const root = rendered.root;
  const element = root.findByType("div");
  expect(element.children.length).toBe(2);
});
