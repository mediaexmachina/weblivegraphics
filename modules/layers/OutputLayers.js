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
import { GraphicKeyer } from "./GraphicKeyer";

export class OutputLayers extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const lastLayers = this.props.lastLayers;
        if (typeof lastLayers == "undefined") {
            return null;
        }

        const visibility = lastLayers.fullBypass ? "hidden" : "visible";
        const downStreamKeyer = lastLayers.downStreamKeyer;

        let allKeyers = [];
        if (lastLayers.keyers != null) {
            const filteredKeyers = lastLayers.keyers
                .slice()
                //.reverse()
                .filter(
                    (k) =>
                        (k.activeProgram &&
                            (pagekind == "program" || pagekind == "clean")) ||
                        (k.activePreview && pagekind == "preview")
                );
            for (let i = 0; i < filteredKeyers.length; i++) {
                const k = filteredKeyers[i];
                allKeyers.push(
                    <GraphicKeyer
                        key={k.id}
                        keyer={k}
                        zindex={100 + i}
                        globalAccess={this.props.globalAccess}
                    />
                );
            }
        }

        if (downStreamKeyer != null && pagekind != "clean") {
            allKeyers.push(
                <GraphicKeyer
                    key={downStreamKeyer.id}
                    keyer={downStreamKeyer}
                    zindex="10000"
                    globalAccess={this.props.globalAccess}
                />
            );
        }

        return (
            <>
                <article className="layers" style={{ visibility: visibility }}>
                    {allKeyers}
                </article>
                <section
                    className="imgbackground"
                    style={{
                        backgroundImage: "url(" + backgroundImageURL + ")",
                    }}
                />
            </>
        );
    }
}
