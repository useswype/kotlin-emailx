package com.swypex.emailx.components

import com.swypex.emailx.Direction
import com.swypex.emailx.escapeXmlAttribute
import kotlinx.html.*

/**
 * A fixed-size background image with an Outlook VML fallback. Content must fit the given height.
 */
data class OutlookBackgroundImage(
    val src: String,
    val width: Int,
    val height: Int,
    val fallbackColor: String = "#ffffff",
) {
    init {
        require(src.isNotBlank()) { "Background image src must not be blank" }
        require(width > 0 && height > 0) { "Background image dimensions must be positive" }
        require(fallbackColor.matches(Regex("#[0-9a-fA-F]{6}"))) {
            "Background fallback must be a six-digit hex color"
        }
    }
}

fun FlowContent.section(
    vararg className: String,
    style: String = "",
    direction: Direction? = null,
    lang: String? = null,
    backgroundImage: OutlookBackgroundImage? = null,
    content: TD.() -> Unit,
) {
    val (outer, inner) = tableAndCellStyles(className.toList())

    table(presentation = true) {
        this.style = listOf("width:100%", outer, style).toCssStyle()

        backgroundFallback(attributes["style"].orEmpty())?.let { attributes["bgcolor"] = it }
        widthFallback(attributes["style"].orEmpty())?.let { attributes["width"] = it }

        tr {
            td {
                direction?.let {
                    attributes["dir"] = it.htmlValue

                    if (it == Direction.RTL) {
                        attributes["align"] = "right"
                    }
                }

                lang?.let { attributes["lang"] = it }

                if (inner.isNotEmpty()) {
                    this.style = "$inner;"
                }

                val backgroundColor =
                    backgroundFallback(attributes["style"].orEmpty())
                        ?: backgroundImage?.fallbackColor

                backgroundColor?.let { attributes["bgcolor"] = it }
                backgroundImage?.let { image ->
                    attributes["background"] = image.src
                    val src = escapeXmlAttribute(image.src)
                    val color = escapeXmlAttribute(backgroundColor ?: image.fallbackColor)
                    unsafe {
                        +"<!--[if mso]><v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"width:${image.width}px;height:${image.height}px;\"><v:fill type=\"frame\" src=\"$src\" color=\"$color\"/><v:textbox inset=\"0,0,0,0\"><![endif]-->"
                    }
                }

                content()

                if (backgroundImage != null) {
                    unsafe { +"<!--[if mso]></v:textbox></v:rect><![endif]-->" }
                }
            }
        }
    }
}
