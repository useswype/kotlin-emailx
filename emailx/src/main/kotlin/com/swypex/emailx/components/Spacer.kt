package com.swypex.emailx.components

import kotlinx.html.*

/** A fixed-height gap, using a table cell for clients that ignore CSS margins. */
fun FlowContent.spacer(height: Int) {
    require(height > 0) { "height must be positive" }
    table(presentation = true) {
        attributes["aria-hidden"] = "true"

        tr {
            td {
                attributes["height"] = height.toString()
                style = "height:${height}px;font-size:0;line-height:0;mso-line-height-rule:exactly;"
                +"\u00a0"
            }
        }
    }
}
