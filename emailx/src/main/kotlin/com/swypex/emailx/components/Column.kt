package com.swypex.emailx.components

import kotlinx.html.*

fun TR.column(
    vararg className: String,
    width: String? = null,
    style: String = "",
    content: TD.() -> Unit,
) {
    td {
        if (width != null) {
            attributes["width"] = width
        }

        this.style = componentCss("vertical-align:top", className.toList(), style)

        backgroundFallback(attributes["style"].orEmpty())?.let { attributes["bgcolor"] = it }
        content()
    }
}
