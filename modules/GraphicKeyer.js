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
import { GraphicItem } from "./GraphicItem";

export class GraphicKeyer extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        var keyer = this.props.keyer;
        const allItems = keyer.items
            .map(i => <GraphicItem key={i.id} item={i} />);

        return (
            <div>
                {allItems}
            </div>
        );
        /*
        private boolean activeProgram; //TODO manage program/preview
        private boolean activePreview;
        */
    }
}
