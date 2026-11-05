package com.swypex.emailx.components

import kotlinx.html.*

fun FlowContent.image(
    vararg className: String,
    src: String,
    alt: String,
    width: Int? = null,
    height: Int? = null,
    style: String = "",
) {
    require(width == null || width > 0) { "width must be positive" }
    require(height == null || height > 0) { "height must be positive" }

    img(src = src, alt = alt) {
        if (width != null) {
            attributes["width"] = width.toString()
        }

        if (height != null) {
            attributes["height"] = height.toString()
        }

        this.style =
            componentCss(
                "display:block;border:0;max-width:100%" +
                    if (height == null) ";height:auto" else "",
                className.toList(),
                style,
            )
    }
}
