package com.swypex.emailx.components

import com.swypex.emailx.tailcss.tailcssDeclarations
import kotlinx.html.*

private val defaultStyles =
    listOf(
        "border-top:1px solid #dddddd",
        "font-size:0",
        "line-height:0",
        "mso-line-height-rule:exactly",
    )

/** A decorative rule rendered as a presentation table for email clients. */
fun FlowContent.divider(vararg className: String, style: String = "") {
    val declarations = tailcssDeclarations(className.toList()) + style.toCssStyleList()
    val (border, layout) = declarations.partitionByProperty("border")

    val tableStyle = (listOf("width:100%", "margin:16px 0") + layout).toCssStyle()

    table(width = widthFallback(tableStyle) ?: "100%", presentation = true) {
        attributes["aria-hidden"] = "true"

        if (
            layout.any { it.trim() == "margin-left:auto" } &&
                layout.any { it.trim() == "margin-right:auto" }
        ) {
            attributes["align"] = "center"
        }

        this.style = tableStyle

        tr {
            td {
                attributes["height"] = "1"
                this.style = (defaultStyles + border).toCssStyle()

                +"\u00a0"
            }
        }
    }
}
