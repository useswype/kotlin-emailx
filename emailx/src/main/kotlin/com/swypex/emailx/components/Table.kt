package com.swypex.emailx.components

import kotlinx.html.*
import kotlinx.html.table as htmlTable

// A semantic table with email-friendly spacing defaults
fun FlowContent.table(
    vararg className: String,
    width: String? = "100%",
    style: String = "",
    presentation: Boolean = false,
    content: TABLE.() -> Unit,
) {
    htmlTable {
        width?.let { attributes["width"] = it }
        attributes["cellpadding"] = "0"
        attributes["cellspacing"] = "0"
        attributes["border"] = "0"

        if (presentation) {
            attributes["role"] = "presentation"
        }

        if (className.isNotEmpty() || style.isNotBlank()) {
            this.style = componentCss("", className.toList(), style)
            widthFallback(attributes["style"].orEmpty())?.let { attributes["width"] = it }
            backgroundFallback(attributes["style"].orEmpty())?.let { attributes["bgcolor"] = it }
        }
        content()
    }
}
