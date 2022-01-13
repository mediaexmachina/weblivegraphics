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
'use strict';

import css from './app.scss'; //NOSONAR S1128
import { Component } from 'react';
import { render as _render } from 'react-dom';
import { Main } from './main';

class App extends Component {

	constructor(props) {
		super(props);
	}

	render() {
		return (<Main />)
	}
}

_render(
	<App />,
	document.getElementById('react')
)
