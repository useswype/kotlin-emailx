package com.swypex.emailx.components

import com.swypex.emailx.tailcss.tailcssDeclarations
import com.swypex.emailx.tailcss.tailcssStyle

// We decided against keeping a css style mapping for each element keep things simple with
// a style string implementation. The downside is that sometimes we need to find a specific
// CSS style and extract the value from it. This is rare and the complexity of copying maps
// is not worth it. For the sake of simplicty and easier developer experice (writing
// Tailwind CSS utility classes instead of building Kotlin maps) we kept this design around.
//
// There are good arguments against this decision. Perhaps as this library becomes more complex
// we will roll back this decision.

internal fun String.toCssStyleList(): List<String> = split(';').filter { it.isNotBlank() }

internal fun String.findPropertyOrNull(property: String): String? =
    toCssStyleList().findPropertyOrNull(property)

internal fun List<String>.findPropertyOrNull(property: String): String? =
    lastOrNull { declaration ->
        ':' in declaration &&
            declaration.substringBefore(':').trim().equals(property, ignoreCase = true)
    }
    ?.substringAfter(':')
    ?.trim()

internal fun List<String>.toCssStyle(): String = map {
    it.trim().trimEnd(';')
}
    .filter { it.isNotBlank() }
    .joinToString(";")
    .let { if (it.isEmpty()) it else "$it;" }

internal fun componentCss(defaults: String, className: List<String>, style: String): String =
    listOf(defaults, tailcssStyle(className), style).toCssStyle()

internal fun List<String>.partitionByProperty(
    vararg prefixes: String,
): Pair<List<String>, List<String>> = partition { declaration ->
    val property = declaration.trim().substringBefore(':')
    prefixes.any { property.startsWith(it) }
}

// Table cells ignore margins, so apply outer spacing to the table itself.
internal fun tableAndCellStyles(className: List<String>): Pair<String, String> {
    val declarations = tailcssDeclarations(className)
    val (outer, inner) = declarations.partitionByProperty("margin")

    return outer.joinToString(";") to inner.joinToString(";")
}

// Extract an opaque hexadecimal CSS background for legacy email clients.
internal fun backgroundFallback(style: String): String? {
    var color: String? = null
    style.toCssStyleList().forEach { declaration ->
        val property = declaration.substringBefore(':').trim().lowercase()

        if (property == "background" || property == "background-color") {
            color = declaration.substringAfter(':', "").trim()
        }
    }

    return color
}

// Only simple pixel and percentage widths can be represented by an HTML width attribute.
internal fun widthFallback(style: String): String? {
    val value = style.findPropertyOrNull("width") ?: return null
    return when {
        value.matches(Regex("[0-9]+px")) -> value.removeSuffix("px")
        value.matches(Regex("[0-9]+%")) -> value
        else -> null
    }
}
