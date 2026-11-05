package com.swypex.emailx.components

import kotlinx.html.*

fun FlowContent.row(
    vararg className: String,
    style: String = "",
    content: TR.() -> Unit,
) {
    table(presentation = true) {
        this.style = componentCss("width:100%", className.toList(), style)

        backgroundFallback(attributes["style"].orEmpty())?.let { attributes["bgcolor"] = it }
        widthFallback(attributes["style"].orEmpty())?.let { attributes["width"] = it }

        tr {
            content()
        }
    }
}
