package com.swypex.emailx.components

import kotlinx.html.*

fun FlowContent.link(
    vararg className: String,
    href: String,
    style: String = "",
    target: String? = "_blank",
    content: A.() -> Unit,
) {
    a(href = href) {
        target?.let { attributes["target"] = it }

        if (style.isNotEmpty() || className.isNotEmpty()) {
            this.style = componentCss("", className.toList(), style)
        }

        content()
    }
}
