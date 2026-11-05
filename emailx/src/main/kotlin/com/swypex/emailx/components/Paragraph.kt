package com.swypex.emailx.components

import com.swypex.emailx.Direction
import kotlinx.html.*

fun FlowContent.paragraph(
    vararg className: String,
    style: String = "",
    direction: Direction? = null,
    content: P.() -> Unit,
) {
    p {
        direction?.let { attributes["dir"] = it.htmlValue }
        this.style = componentCss("margin:0 0 16px;line-height:1.5", className.toList(), style)

        content()
    }
}
