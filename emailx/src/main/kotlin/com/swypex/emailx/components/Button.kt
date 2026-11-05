package com.swypex.emailx.components

import com.swypex.emailx.Direction
import com.swypex.emailx.tailcss.tailcssDeclarations
import kotlinx.html.*

fun FlowContent.button(
    vararg className: String,
    href: String,
    style: String = "",
    backgroundColor: String = "#222222",
    color: String = "#ffffff",
    padding: String = "12px 24px",
    direction: Direction? = null,
    target: String? = "_blank",
    content: A.() -> Unit,
) {
    val declarations = tailcssDeclarations(className.toList())

    // These properties need to be defined on the table cell not the link inside.
    val (cellCss, linkCss) =
        declarations.partitionByProperty("background", "padding", "border-radius", "border-color")

    val cellBackground = cellCss.findPropertyOrNull("background-color") ?: backgroundColor

    val renderButton: FlowContent.() -> Unit = {
        table(width = null, presentation = true) {
            direction?.let { attributes["dir"] = it.htmlValue }

            this.style = "border-collapse:separate;mso-table-lspace:0pt;mso-table-rspace:0pt;"

            tr {
                td {
                    attributes["bgcolor"] = cellBackground

                    this.style =
                        componentCss(
                            "background-color:$backgroundColor;padding:$padding;border-radius:4px",
                            emptyList(),
                            cellCss.joinToString(";"),
                        )

                    a(href = href) {
                        target?.let { attributes["target"] = it }

                        this.style =
                            componentCss(
                                "color:$color;text-decoration:none;display:inline-block",
                                emptyList(),
                                (linkCss + style.toCssStyleList()).toCssStyle(),
                            )
                        content()
                    }
                }
            }
        }
    }

    if (direction == Direction.RTL) {
        table(presentation = true) {
            tr {
                td {
                    attributes["align"] = "right"
                    renderButton()
                }
            }
        }
    } else {
        renderButton()
    }
}
