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
"use strict";

import React, { Component } from "react";

export class GraphicItem extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const item = this.props.item;
        if (item.active == false) {
            return null;
        }
        /*
    	private UUID id;
	    private String label;
	    private Map<String, Object> setup;
        */
        return <div>{item.typeName}</div>;
    }
}
