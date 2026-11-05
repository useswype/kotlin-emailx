package com.swypex.emailx.components

import kotlinx.html.*

fun FlowContent.container(
    vararg className: String,
    width: Int = 600,
    style: String = "",
    cellStyle: String = "",
    content: TD.() -> Unit,
) {
    require(width > 0) { "width must be positive" }

    val (outer, inner) = tableAndCellStyles(className.toList())

    (this as HTMLTag).unsafe {
        +"<!--[if mso]><table role=\"presentation\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"$width\"><tr><td><![endif]-->"
    }

    table(width = width.toString(), presentation = true) {
        attributes["align"] = "center"

        this.style =
            listOf("width:100%;max-width:${width}px;margin:0 auto", outer, style).toCssStyle()

        backgroundFallback(attributes["style"].orEmpty())?.let { attributes["bgcolor"] = it }

        tr {
            td {
                if (cellStyle.isNotEmpty() || inner.isNotEmpty()) {
                    this.style = listOf(inner, cellStyle).toCssStyle()
                }

                backgroundFallback(attributes["style"].orEmpty())?.let {
                    attributes["bgcolor"] = it
                }
                content()
            }
        }
    }

    (this as HTMLTag).unsafe {
        +"<!--[if mso]></td></tr></table><![endif]-->"
    }
}
