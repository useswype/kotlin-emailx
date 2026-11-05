package com.swypex.emailx.components

import com.swypex.emailx.Direction
import kotlinx.html.*

fun FlowContent.heading(
    vararg className: String,
    level: Int = 1,
    style: String = "",
    direction: Direction? = null,
    content: FlowContent.() -> Unit,
) {
    require(level in 1..6) { "heading level must be between 1 and 6" }
    when (level) {
        1 ->
            h1 {
                direction?.let { attributes["dir"] = it.htmlValue }
                this.style = componentCss("margin:0 0 16px", className.toList(), style)
                content()
            }
        2 ->
            h2 {
                direction?.let { attributes["dir"] = it.htmlValue }
                this.style = componentCss("margin:0 0 16px", className.toList(), style)
                content()
            }
        3 ->
            h3 {
                direction?.let { attributes["dir"] = it.htmlValue }
                this.style = componentCss("margin:0 0 16px", className.toList(), style)
                content()
            }
        4 ->
            h4 {
                direction?.let { attributes["dir"] = it.htmlValue }
                this.style = componentCss("margin:0 0 16px", className.toList(), style)
                content()
            }
        5 ->
            h5 {
                direction?.let { attributes["dir"] = it.htmlValue }
                this.style = componentCss("margin:0 0 16px", className.toList(), style)
                content()
            }
        6 ->
            h6 {
                direction?.let { attributes["dir"] = it.htmlValue }
                this.style = componentCss("margin:0 0 16px", className.toList(), style)
                content()
            }
    }
}
